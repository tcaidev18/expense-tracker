# ExpenseTrackerAndroid

Android expense tracker — MVVM, Jetpack Compose, Material 3, offline-first (Room + DataStore).

## Tech stack

- Kotlin 2.2, Compose BOM, Material 3
- Hilt (DI), Room (local DB shell), Navigation Compose, DataStore
- Min SDK 24, Target SDK 37

## Build

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
```

File-scoped unit test:

```bash
./gradlew :app:testDebugUnitTest --tests "com.aitc.expensetrackerandroid.util.MoneyFormatterTest"
```

## Package structure

| Package | Role |
|---------|------|
| `core/` | `AppResult`, `UiState`, `BaseViewModel`, `DispatcherProvider`, extensions |
| `data/` | Room database shell, `AppPreferences`, converters |
| `repository/` | `BaseRepository` |
| `navigation/` | Routes, `AppNavGraph`, nav extensions |
| `ui/` | Theme tokens, `StateHandler` composables |
| `util/` | `MoneyFormatter`, `DateFormatter` |
| `di/` | Hilt modules |

## Docs

- [Architecture](docs/ARCHITECTURE.md)
- [Adding a feature](docs/ADDING_FEATURE.md)
- [Agent workflow](docs/AGENT_WORKFLOW.md)
- [AGENTS.md](AGENTS.md) — instructions for AI agents

## Conventions

- Feature ViewModels extend `BaseViewModel`; repositories extend `BaseRepository`
- UI state via `UiState<T>` + `StateFlow`; screens use `StateHandler`
- No business logic in Composables; strings in `strings.xml`
- Money: store as `Long`, format with `MoneyFormatter` (default VND)
