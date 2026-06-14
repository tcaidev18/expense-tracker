# Architecture

## Layers

```
Screen (Compose)
    ↓ collects StateFlow
ViewModel extends BaseViewModel
    ↓
Repository extends BaseRepository
    ↓
Room DAO / AppPreferences
```

## Core types

- **`AppResult<T>`** — one-shot outcomes: Success, Error, Loading
- **`UiState<T>`** — screen state: loading, data, error
- **`DispatcherProvider`** — injectable dispatchers for tests

## Data

- **Room** — `ExpenseTrackerDatabase` v1 with internal `AppMetadataEntity` only
- **DataStore** — `AppPreferences` generic wrapper; feature keys defined per feature

## Adding features

See [ADDING_FEATURE.md](ADDING_FEATURE.md).
