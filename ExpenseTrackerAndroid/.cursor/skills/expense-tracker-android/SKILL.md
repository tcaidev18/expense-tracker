---
name: expense-tracker-android
description: Orchestrates development on ExpenseTrackerAndroid — MVVM base, Room shell, Compose. Use when adding features, screens, repositories, or working in this Android expense tracker repo.
---

# ExpenseTrackerAndroid Skill

## Before coding

1. Read `docs/ADDING_FEATURE.md` for the feature checklist
2. Read `AGENTS.md` for build commands and conventions
3. Reuse base types — do not duplicate patterns

## Base types (must reuse)

| Type | Location | Use |
|------|----------|-----|
| `BaseViewModel` | `core/viewmodel/` | All feature ViewModels |
| `BaseRepository` | `repository/` | All feature repositories |
| `UiState<T>` | `core/ui/` | Screen state |
| `AppResult<T>` | `core/result/` | One-shot repo results |
| `StateHandler` | `ui/components/state/` | Loading/error/empty/content |
| `AppPreferences` | `data/preferences/` | DataStore access |
| `Route` | `navigation/` | Type-safe routes |

## Adding a feature (order)

1. Entity + DAO → DB migration
2. Model + mapper
3. Repository extends `BaseRepository`
4. ViewModel extends `BaseViewModel` + `@HiltViewModel`
5. Screen with `StateHandler` + `hiltViewModel()`
6. Route + `AppNavGraph` destination
7. Unit tests

## Global skills to invoke

- `android-jetpack-compose-expert` — Compose UI work
- `kotlin-coroutines-expert` — Flow / coroutines
- `test-driven-development` — new features
- `find-bugs` — review before merge

## Prompt template

```
Theo docs/ADDING_FEATURE.md, thêm feature "{name}".
Extend BaseViewModel, BaseRepository. Dùng StateHandler.
Strings tiếng Việt. Không logic trong Composable.
```

## Verify

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```
