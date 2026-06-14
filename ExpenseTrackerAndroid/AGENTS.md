# ExpenseTrackerAndroid — Agent Instructions

## Project

Offline expense tracker, Android, MVVM, Jetpack Compose, Room, Hilt.

## Build

| Task | Command |
|------|---------|
| Debug APK | `./gradlew assembleDebug` |
| Unit tests | `./gradlew testDebugUnitTest` |
| Instrumented | `./gradlew connectedDebugAndroidTest` |
| Single test | `./gradlew :app:testDebugUnitTest --tests "com.aitc.expensetrackerandroid.ClassName"` |

## Package map

- `core/` — AppResult, UiState, BaseViewModel, DispatcherProvider, extensions
- `data/` — Room shell, AppPreferences, DateConverters
- `repository/` — BaseRepository
- `navigation/` — Route, AppNavGraph, NavExtensions
- `ui/` — theme, StateHandler
- `util/` — MoneyFormatter, DateFormatter
- `di/` — Hilt modules

## Conventions

- Feature ViewModel **extends** `BaseViewModel`
- Feature Repository **extends** `BaseRepository`
- Screen state: `UiState<T>` + `StateHandler`
- No DAO/repository calls from Composables
- No hardcoded UI strings — use `strings.xml`
- Money stored as `Long`; format with `MoneyFormatter` (default VND)

## Adding features

Follow [docs/ADDING_FEATURE.md](docs/ADDING_FEATURE.md).

## Agent workflow

See [docs/AGENT_WORKFLOW.md](docs/AGENT_WORKFLOW.md).

## Commit attribution

AI commits MUST include:

```
Co-Authored-By: Cursor <cursoragent@cursor.com>
```
