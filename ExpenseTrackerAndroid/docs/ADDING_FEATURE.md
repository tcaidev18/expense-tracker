# Adding a Feature

Checklist when adding a new feature (e.g. expenses, categories):

## 1. Data layer

- [ ] Create `@Entity` + `@Dao` in `data/local/entity/` and `data/local/dao/`
- [ ] Register entity in `ExpenseTrackerDatabase`; bump `version`
- [ ] Add Room migration if app was released (export schema in `app/schemas/`)
- [ ] Reuse `DateConverters` for date fields

## 2. Model

- [ ] Create domain model in `model/` (UI-facing, no Room annotations)
- [ ] Add mapper functions: `Entity.toModel()` / `Model.toEntity()`

## 3. Repository

- [ ] Create repository extending `BaseRepository`
- [ ] Use `onIo { }` for suspend DB work
- [ ] Expose `Flow` for observe; return `AppResult` for one-shot ops
- [ ] `@Inject constructor` — no manual module unless interface binding needed

## 4. ViewModel

- [ ] Extend `BaseViewModel`
- [ ] Define `FeatureUiState` data class
- [ ] Use `MutableStateFlow<UiState<...>>` + `launchCollect` / `launchSafe`
- [ ] Annotate `@HiltViewModel`

## 5. UI

- [ ] Create screen in `ui/screens/{feature}/`
- [ ] Use `StateHandler(state) { data -> ... }`
- [ ] Collect state: `viewModel.uiState.collectAsStateWithLifecycle()`
- [ ] Strings in `res/values/strings.xml`
- [ ] Format money/date via `MoneyFormatter` / `DateFormatter`

## 6. Navigation

- [ ] Add route to `Route` sealed interface
- [ ] Register `composable(...)` in `AppNavGraph`
- [ ] Navigate via `navController.navigateSingleTop(route)`

## 7. Tests

- [ ] Repository: in-memory Room (`Room.inMemoryDatabaseBuilder`)
- [ ] ViewModel: `runTest` + `StandardTestDispatcher` + `Dispatchers.setMain`
- [ ] Pure utils: JVM unit tests in `src/test/`

## Template prompt

```
Theo docs/ADDING_FEATURE.md, thêm feature "{name}":
Entity, DAO, migration, Model, Repository, ViewModel, Screen, Route.
Dùng BaseViewModel, BaseRepository, StateHandler. Không logic trong Composable.
```
