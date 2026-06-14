#!/usr/bin/env bash
# Scaffold a presentation-layer screen (ViewModel, Screen, Route, strings, NavGraph).
# Usage: ./scripts/new_screen.sh FeatureName [--dry-run] [--route path] [--title "Title"] [--arg name:Type]

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
JAVA_BASE="$REPO_ROOT/app/src/main/java/com/aitc/expensetrackerandroid"
STRINGS_FILE="$REPO_ROOT/app/src/main/res/values/strings.xml"
ROUTE_FILE="$JAVA_BASE/navigation/Route.kt"
NAV_FILE="$JAVA_BASE/navigation/AppNavGraph.kt"

DRY_RUN=false
FEATURE_INPUT=""
ROUTE_OVERRIDE=""
TITLE_OVERRIDE=""
ARG_SPEC=""
HAS_NAV_ARG=false
ARG_NAME=""
ARG_TYPE=""
ARG_CAMEL=""
ARG_CONST=""
ARG_REQUIRE_FUN=""
ARG_NAV_TYPE=""

usage() {
    cat <<'EOF'
Usage: ./scripts/new_screen.sh <FeatureName> [options]

Scaffolds ViewModel, Screen, Route entry, strings, and AppNavGraph destination.

Options:
  --dry-run          Print planned changes without writing files
  --route <path>     Override navigation route path (default: snake_case of name)
  --title <text>     Override strings.xml title (default: FeatureName)
  --arg <name:Type>  Navigation argument (Type: Long, String, or Int). One arg per screen.
  --help             Show this help

Examples:
  ./scripts/new_screen.sh ExpenseList
  ./scripts/new_screen.sh expense-list --dry-run
  ./scripts/new_screen.sh ExpenseList --route expense_list --title "Danh sách chi tiêu"
  ./scripts/new_screen.sh ExpenseDetail --arg expenseId:Long --title "Chi tiết chi tiêu"
EOF
}

log() {
    printf '%s\n' "$*"
}

die() {
    printf 'Error: %s\n' "$*" >&2
    exit 1
}

parse_args() {
    if [[ $# -eq 0 ]]; then
        usage
        exit 1
    fi

    while [[ $# -gt 0 ]]; do
        case "$1" in
            --dry-run)
                DRY_RUN=true
                shift
                ;;
            --route)
                [[ $# -ge 2 ]] || die "--route requires a value"
                ROUTE_OVERRIDE="$2"
                shift 2
                ;;
            --title)
                [[ $# -ge 2 ]] || die "--title requires a value"
                TITLE_OVERRIDE="$2"
                shift 2
                ;;
            --arg)
                [[ $# -ge 2 ]] || die "--arg requires a value (format: name:Type)"
                [[ -z "$ARG_SPEC" ]] || die "Only one --arg is supported per screen"
                ARG_SPEC="$2"
                shift 2
                ;;
            --help|-h)
                usage
                exit 0
                ;;
            -*)
                die "Unknown option: $1"
                ;;
            *)
                [[ -z "$FEATURE_INPUT" ]] || die "Only one feature name allowed"
                FEATURE_INPUT="$1"
                shift
                ;;
        esac
    done

    [[ -n "$FEATURE_INPUT" ]] || die "Feature name is required"
}

# Split camelCase / kebab-case / snake_case into words.
split_words() {
    local input="$1"
    local step1 step2
    step1="$(printf '%s' "$input" | sed 's/[-_]/ /g')"
    step2="$(printf '%s' "$step1" | sed 's/\([a-z0-9]\)\([A-Z]\)/\1 \2/g')"
    printf '%s' "$step2" | tr '[:upper:]' '[:lower:]'
}

to_pascal_case() {
    local words
    read -r -a words <<< "$(split_words "$1")"
    local word pascal="" part
    for word in "${words[@]}"; do
        [[ -n "$word" ]] || continue
        part="$(printf '%s' "${word:0:1}" | tr '[:lower:]' '[:upper:]')${word:1}"
        pascal+="$part"
    done
    [[ -n "$pascal" ]] || die "Invalid feature name: $1"
    printf '%s' "$pascal"
}

pascal_to_snake() {
    printf '%s' "$1" | sed 's/\([A-Z]\)/_\1/g' | sed 's/^_//' | tr '[:upper:]' '[:lower:]'
}

pascal_to_package() {
    tr '[:upper:]' '[:lower:]' <<< "$1"
}

camel_to_screaming_snake() {
    printf '%s' "$1" | sed 's/\([A-Z]\)/_\1/g' | sed 's/^_//' | tr '[:lower:]' '[:upper:]'
}

parse_nav_arg() {
    [[ -n "$ARG_SPEC" ]] || return 0

    if [[ "$ARG_SPEC" != *:* ]]; then
        die "--arg must use name:Type format (e.g. expenseId:Long)"
    fi

    ARG_NAME="${ARG_SPEC%%:*}"
    ARG_TYPE="${ARG_SPEC#*:}"
    ARG_CAMEL="$ARG_NAME"

    case "$ARG_TYPE" in
        Long)
            ARG_REQUIRE_FUN="requireLongArg"
            ARG_NAV_TYPE="Long"
            ;;
        String)
            ARG_REQUIRE_FUN="requireStringArg"
            ARG_NAV_TYPE="String"
            ;;
        Int)
            ARG_REQUIRE_FUN="requireIntArg"
            ARG_NAV_TYPE="Int"
            ;;
        *)
            die "Unsupported --arg type: $ARG_TYPE (use Long, String, or Int)"
            ;;
    esac

    if [[ ! "$ARG_NAME" =~ ^[a-z][a-zA-Z0-9]*$ ]]; then
        die "Invalid argument name: $ARG_NAME (camelCase, e.g. expenseId)"
    fi

    ARG_CONST="ARG_$(camel_to_screaming_snake "$ARG_NAME")"
    HAS_NAV_ARG=true
}

normalize_name() {
    PASCAL="$(to_pascal_case "$FEATURE_INPUT")"
    PACKAGE_FOLDER="$(pascal_to_package "$PASCAL")"
    ROUTE_PATH="${ROUTE_OVERRIDE:-$(pascal_to_snake "$PASCAL")}"
    STRING_KEY="${ROUTE_PATH}_title"
    DISPLAY_TITLE="${TITLE_OVERRIDE:-$PASCAL}"

    if [[ ! "$ROUTE_PATH" =~ ^[a-z][a-z0-9_]*$ ]]; then
        die "Route path must be lowercase snake_case: $ROUTE_PATH"
    fi
    if [[ ! "$PASCAL" =~ ^[A-Z][A-Za-z0-9]*$ ]]; then
        die "Invalid PascalCase name: $PASCAL"
    fi
}

assert_not_exists() {
    local vm_file="$SCREEN_DIR/${PASCAL}ViewModel.kt"
    local screen_file="$SCREEN_DIR/${PASCAL}Screen.kt"

    if [[ -f "$vm_file" ]]; then
        die "ViewModel already exists: $vm_file"
    fi
    if [[ -f "$screen_file" ]]; then
        die "Screen already exists: $screen_file"
    fi
    if grep -q "data object ${PASCAL}" "$ROUTE_FILE" 2>/dev/null; then
        die "Route already exists: data object ${PASCAL}"
    fi
    if grep -q "data class ${PASCAL}" "$ROUTE_FILE" 2>/dev/null; then
        die "Route already exists: data class ${PASCAL}"
    fi
    if grep -q "name=\"${STRING_KEY}\"" "$STRINGS_FILE" 2>/dev/null; then
        die "String resource already exists: ${STRING_KEY}"
    fi
}

write_viewmodel() {
    local file="$SCREEN_DIR/${PASCAL}ViewModel.kt"
    local package="com.aitc.expensetrackerandroid.ui.screens.${PACKAGE_FOLDER}"

    if $DRY_RUN; then
        log "[dry-run] Would create: $file"
        return
    fi

    mkdir -p "$SCREEN_DIR"

    if $HAS_NAV_ARG; then
        cat > "$file" <<EOF
package ${package}

import androidx.lifecycle.SavedStateHandle
import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import com.aitc.expensetrackerandroid.navigation.Route
import com.aitc.expensetrackerandroid.navigation.${ARG_REQUIRE_FUN}
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ${PASCAL}ViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(dispatchers) {

    private val ${ARG_CAMEL}: ${ARG_TYPE} = savedStateHandle.${ARG_REQUIRE_FUN}(Route.${PASCAL}.${ARG_CONST})

    private val _uiState = MutableStateFlow(UiState<${PASCAL}UiState>())
    val uiState: StateFlow<UiState<${PASCAL}UiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        _uiState.launchCollect(
            flow = flowOf(
                ${PASCAL}UiState(
                    ${ARG_CAMEL} = ${ARG_CAMEL},
                ),
            ),
        )
    }
}

data class ${PASCAL}UiState(
    val ${ARG_CAMEL}: ${ARG_TYPE},
    val placeholder: String = "",
)
EOF
        return
    fi

    cat > "$file" <<EOF
package ${package}

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ${PASCAL}ViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow(UiState<${PASCAL}UiState>())
    val uiState: StateFlow<UiState<${PASCAL}UiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        _uiState.launchCollect(flow = flowOf(${PASCAL}UiState()))
    }
}

data class ${PASCAL}UiState(
    val placeholder: String = "",
)
EOF
}

write_screen() {
    local file="$SCREEN_DIR/${PASCAL}Screen.kt"
    local package="com.aitc.expensetrackerandroid.ui.screens.${PACKAGE_FOLDER}"

    if $DRY_RUN; then
        log "[dry-run] Would create: $file"
        return
    fi

    mkdir -p "$SCREEN_DIR"
    cat > "$file" <<EOF
package ${package}

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler

@Composable
fun ${PASCAL}Screen(
    viewModel: ${PASCAL}ViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
    ) { data ->
        ${PASCAL}Content(data = data)
    }
}

@Composable
private fun ${PASCAL}Content(data: ${PASCAL}UiState) {
    // TODO: UI
    Text(text = stringResource(R.string.${STRING_KEY}))
}
EOF
}

patch_route() {
    if grep -q "data object ${PASCAL}" "$ROUTE_FILE" 2>/dev/null \
        || grep -q "data class ${PASCAL}" "$ROUTE_FILE" 2>/dev/null; then
        log "Route ${PASCAL} already present — skipping Route.kt patch"
        return
    fi

    if $DRY_RUN; then
        log "[dry-run] Would patch: $ROUTE_FILE (add ${PASCAL})"
        return
    fi

    grep -q '// SCAFFOLD:ROUTES' "$ROUTE_FILE" || die "Missing // SCAFFOLD:ROUTES marker in Route.kt"

    if $HAS_NAV_ARG; then
        sed -i '' "/\/\/ SCAFFOLD:ROUTES/i\\
    data class ${PASCAL}(val ${ARG_CAMEL}: ${ARG_TYPE}) : Route {\\
        override val path: String = route(${ARG_CAMEL})\\
\\
        companion object {\\
            const val pattern: String = \"${ROUTE_PATH}/{${ARG_NAME}}\"\\
            const val ${ARG_CONST}: String = \"${ARG_NAME}\"\\
\\
            fun route(${ARG_CAMEL}: ${ARG_TYPE}): String = \"${ROUTE_PATH}/\${${ARG_CAMEL}}\"\\
        }\\
    }\\
\\
" "$ROUTE_FILE"
        return
    fi

    sed -i '' "/\/\/ SCAFFOLD:ROUTES/i\\
    data object ${PASCAL} : Route {\\
        override val path: String = \"${ROUTE_PATH}\"\\
    }\\
\\
" "$ROUTE_FILE"
}

patch_nav_graph() {
    if grep -q "${PASCAL}.pattern" "$NAV_FILE" 2>/dev/null \
        || grep -q "composable(${PASCAL}.path)" "$NAV_FILE" 2>/dev/null; then
        log "Nav destination ${PASCAL} already present — skipping AppNavGraph.kt patch"
        return
    fi

    if $DRY_RUN; then
        log "[dry-run] Would patch: $NAV_FILE (imports + composable ${PASCAL})"
        return
    fi

    grep -q '// SCAFFOLD:IMPORTS' "$NAV_FILE" || die "Missing // SCAFFOLD:IMPORTS marker in AppNavGraph.kt"
    grep -q '// SCAFFOLD:DESTINATIONS' "$NAV_FILE" || die "Missing // SCAFFOLD:DESTINATIONS marker in AppNavGraph.kt"

    if $HAS_NAV_ARG && ! grep -q "import androidx.navigation.NavType" "$NAV_FILE"; then
        sed -i '' '/import androidx.navigation.compose.composable/a\
import androidx.navigation.NavType\
import androidx.navigation.navArgument\
' "$NAV_FILE"
    fi

    sed -i '' "/\/\/ SCAFFOLD:IMPORTS/i\\
import com.aitc.expensetrackerandroid.navigation.Route.${PASCAL}\\
import com.aitc.expensetrackerandroid.ui.screens.${PACKAGE_FOLDER}.${PASCAL}Screen\\
" "$NAV_FILE"

    if $HAS_NAV_ARG; then
        sed -i '' "/\/\/ SCAFFOLD:DESTINATIONS/i\\
        composable(\\
            route = ${PASCAL}.pattern,\\
            arguments = listOf(\\
                navArgument(${PASCAL}.${ARG_CONST}) {\\
                    type = NavType.${ARG_NAV_TYPE}Type\\
                },\\
            ),\\
        ) {\\
            ${PASCAL}Screen()\\
        }\\
" "$NAV_FILE"
        return
    fi

    sed -i '' "/\/\/ SCAFFOLD:DESTINATIONS/i\\
        composable(${PASCAL}.path) {\\
            ${PASCAL}Screen()\\
        }\\
" "$NAV_FILE"
}

patch_strings() {
    if grep -q "name=\"${STRING_KEY}\"" "$STRINGS_FILE"; then
        log "String ${STRING_KEY} already present — skipping strings.xml patch"
        return
    fi

    if $DRY_RUN; then
        log "[dry-run] Would patch: $STRINGS_FILE (add ${STRING_KEY})"
        return
    fi

    local escaped_title
    escaped_title="$(printf '%s' "$DISPLAY_TITLE" | sed 's/&/\&amp;/g; s/</\&lt;/g; s/>/\&gt;/g; s/"/\\"/g')"

    sed -i '' "s|</resources>|    <string name=\"${STRING_KEY}\">${escaped_title}</string>\\
</resources>|" "$STRINGS_FILE"
}

print_summary() {
    cat <<EOF

Scaffold summary
----------------
Feature:      ${PASCAL}
Package:      com.aitc.expensetrackerandroid.ui.screens.${PACKAGE_FOLDER}
Route path:   ${ROUTE_PATH}$([[ "$HAS_NAV_ARG" == true ]] && printf ' (%s/{%s})' "$ROUTE_PATH" "$ARG_NAME")
String key:   ${STRING_KEY}
Nav argument: $([[ "$HAS_NAV_ARG" == true ]] && printf '%s (%s)' "$ARG_CAMEL" "$ARG_TYPE" || printf 'none')

Created:
  ${SCREEN_DIR}/${PASCAL}ViewModel.kt
  ${SCREEN_DIR}/${PASCAL}Screen.kt

Patched:
  ${ROUTE_FILE}
  ${NAV_FILE}
  ${STRINGS_FILE}

Navigate (example):
$(if $HAS_NAV_ARG; then
    if [[ "$ARG_TYPE" == "Long" ]]; then
        printf '  navController.navigateSingleTop(Route.%s(1L))' "$PASCAL"
    elif [[ "$ARG_TYPE" == "Int" ]]; then
        printf '  navController.navigateSingleTop(Route.%s(1))' "$PASCAL"
    else
        printf '  navController.navigateSingleTop(Route.%s("value"))' "$PASCAL"
    fi
else
    printf '  navController.navigateSingleTop(Route.%s)' "$PASCAL"
fi)

Next steps:
  1. Implement ${PASCAL}Content UI
  2. Wire Repository when real data is needed (see docs/ADDING_FEATURE.md)
  3. ./gradlew assembleDebug && ./gradlew testDebugUnitTest
EOF
}

main() {
    parse_args "$@"
    normalize_name
    parse_nav_arg

    SCREEN_DIR="$JAVA_BASE/ui/screens/${PACKAGE_FOLDER}"

    [[ -f "$ROUTE_FILE" ]] || die "Route.kt not found at $ROUTE_FILE"
    [[ -f "$NAV_FILE" ]] || die "AppNavGraph.kt not found at $NAV_FILE"
    [[ -f "$STRINGS_FILE" ]] || die "strings.xml not found at $STRINGS_FILE"

    assert_not_exists
    write_viewmodel
    write_screen
    patch_route
    patch_nav_graph
    patch_strings
    print_summary

    if $DRY_RUN; then
        log ""
        log "Dry run complete — no files were modified."
    fi
}

main "$@"
