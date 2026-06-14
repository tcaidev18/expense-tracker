# Adding a Feature

Checklist when adding a new feature (e.g. expenses, categories):

## Scaffold screen (presentation only)

Script [`scripts/new_screen.sh`](../scripts/new_screen.sh) tạo nhanh skeleton màn hình (ViewModel, Screen, Route, strings, NavGraph) **không** tạo Entity, DAO, Room migration hay Repository.

Chạy từ **root project** `ExpenseTrackerAndroid/` (thư mục có `gradlew`).

### Cấp quyền thực thi (chỉ lần đầu)

```bash
chmod +x scripts/new_screen.sh
```

### Tạo màn mới

```bash
./scripts/new_screen.sh ExpenseList
```

Tên có thể dùng PascalCase hoặc kebab-case — script tự chuẩn hóa:

```bash
./scripts/new_screen.sh expense-list
```

### Arguments

**Cú pháp:**

```bash
./scripts/new_screen.sh <FeatureName> [options]
```

#### Argument bắt buộc

| Argument | Vị trí | Mô tả | Ví dụ |
|----------|--------|--------|-------|
| `FeatureName` | Đầu tiên (positional) | Tên feature/màn hình. Script chuyển thành PascalCase cho class Kotlin. Chỉ **một** tên mỗi lần chạy. | `ExpenseList`, `expense-list`, `expense_list`, `addExpense` |

**Định dạng `FeatureName` hợp lệ:**

- PascalCase: `ExpenseList`, `AddExpense`
- kebab-case: `expense-list`, `add-expense`
- snake_case: `expense_list`
- camelCase: `addExpense` (tách thành `Add` + `Expense`)

Sau normalize, class phải khớp `[A-Z][A-Za-z0-9]*` — nếu không, script báo lỗi và thoát.

#### Options (tuỳ chọn)

| Option | Giá trị | Bắt buộc value? | Mặc định | Mô tả |
|--------|---------|-----------------|----------|--------|
| `--dry-run` | — | Không | `false` | In preview các file sẽ tạo/sửa; **không** ghi disk |
| `--route` | `<path>` | Có | snake_case của `FeatureName` | Route path trong Navigation Compose (`Route.path`). Phải là lowercase snake_case: `[a-z][a-z0-9_]*` |
| `--title` | `<text>` | Có | Tên PascalCase (vd. `ExpenseList`) | Nội dung string `{route_path}_title` trong `strings.xml` |
| `--arg` | `<name:Type>` | Có | Không có | Navigation argument (`Type`: `Long`, `String`, `Int`). Tạo route có `{arg}`, `navArgument`, đọc arg trong ViewModel qua `SavedStateHandle` |
| `--help`, `-h` | — | Không | — | In help rồi thoát (exit 0) |

**Thứ tự:** options đặt **sau** `FeatureName`, có thể kết hợp:

```bash
./scripts/new_screen.sh ExpenseList --dry-run
./scripts/new_screen.sh ExpenseList --route expense_list --title "Danh sách chi tiêu"
./scripts/new_screen.sh ExpenseDetail --arg expenseId:Long --title "Chi tiết chi tiêu"
./scripts/new_screen.sh add-expense --route add_expense --title "Thêm chi tiêu" --dry-run
```

**Lỗi thường gặp:**

| Lỗi | Nguyên nhân |
|-----|-------------|
| `Feature name is required` | Thiếu `FeatureName` hoặc chỉ truyền flags |
| `Only one feature name allowed` | Truyền nhiều hơn một tên positional |
| `--route requires a value` / `--title requires a value` | Flag thiếu giá trị kế tiếp |
| `Unknown option: ...` | Flag không tồn tại |
| `Route path must be lowercase snake_case` | `--route` sai format (vd. `Expense-List`, `UPPER`) |
| `Unsupported --arg type` | `--arg` dùng type khác `Long`, `String`, `Int` |
| `Invalid argument name` | Tên arg không phải camelCase (vd. phải là `expenseId`) |

Xem help trực tiếp từ script:

```bash
./scripts/new_screen.sh --help
```

### Quy ước đặt tên

| Input | Class | Package folder | Route path | String key |
|-------|-------|----------------|------------|------------|
| `ExpenseList` | `ExpenseList` | `expenselist` | `expense_list` | `expense_list_title` |
| `expense-list` | `ExpenseList` | `expenselist` | `expense_list` | `expense_list_title` |

### Output

**File mới:**

- `app/src/main/java/.../ui/screens/{feature}/{Name}ViewModel.kt` — `@HiltViewModel`, `UiState`, `launchCollect`
- `app/src/main/java/.../ui/screens/{feature}/{Name}Screen.kt` — `StateHandler`, `hiltViewModel()`

**File được patch** (insert trước marker `// SCAFFOLD:*`):

- `navigation/Route.kt` — `data object {Name}`
- `navigation/AppNavGraph.kt` — import + `composable(...)`
- `app/src/main/res/values/strings.xml` — `{route_path}_title`

### Sau khi chạy script

1. Implement UI trong `{Name}Content` (ví dụ `ExpenseListContent`).
2. Verify build:
   ```bash
   ./gradlew assembleDebug
   ./gradlew testDebugUnitTest
   ```
3. Navigate tới màn (từ màn khác):
   ```kotlin
   navController.navigateSingleTop(Route.ExpenseList)
   ```
4. Khi cần data thật, làm tiếp bước 1–3 bên dưới (Entity, Repository, …).

### Lưu ý

- Script **thoát lỗi** nếu ViewModel/Screen/Route/string đã tồn tại (không ghi đè).
- Chỉ scaffold **presentation layer**; Repository và data layer làm thủ công hoặc prompt agent theo checklist dưới.
- Marker `// SCAFFOLD:ROUTES`, `// SCAFFOLD:IMPORTS`, `// SCAFFOLD:DESTINATIONS` trong navigation giúp script insert an toàn — không xóa các dòng này.

### Navigation arguments (truyền tham số giữa các màn)

**Màn không arg** — `data object` + path cố định (vd. `ExpenseList`, `Splash`):

```kotlin
navController.navigateSingleTop(Route.ExpenseList)
```

**Màn có arg** — `data class Route` + `pattern` + đọc arg trong ViewModel:

| Layer | Vai trò |
|-------|---------|
| [`Route.kt`](../app/src/main/java/com/aitc/expensetrackerandroid/navigation/Route.kt) | `data class ExpenseDetail(val expenseId: Long)` với `pattern`, `ARG_*`, `route(id)` |
| [`AppNavGraph.kt`](../app/src/main/java/com/aitc/expensetrackerandroid/navigation/AppNavGraph.kt) | `composable(route = pattern, arguments = listOf(navArgument(...)))` |
| ViewModel | `SavedStateHandle` + `requireLongArg` / `requireStringArg` / `requireIntArg` |
| Screen gọi màn | Callback từ NavGraph (vd. `onOpenExpenseDetail: (Long) -> Unit`) — **không** đọc arg trong Composable |

Ví dụ navigate:

```kotlin
navController.navigateToExpenseDetail(expenseId = 1L)
// hoặc
navController.navigateSingleTop(Route.ExpenseDetail(expenseId = 1L))
```

Đọc arg trong ViewModel (mẫu [`ExpenseDetailViewModel`](../app/src/main/java/com/aitc/expensetrackerandroid/ui/screens/expensedetail/ExpenseDetailViewModel.kt)):

```kotlin
private val expenseId: Long =
    savedStateHandle.requireLongArg(Route.ExpenseDetail.ARG_EXPENSE_ID)
```

Helper: [`SavedStateHandleExt.kt`](../app/src/main/java/com/aitc/expensetrackerandroid/navigation/SavedStateHandleExt.kt).

Scaffold màn có arg:

```bash
./scripts/new_screen.sh ExpenseDetail --arg expenseId:Long --title "Chi tiết chi tiêu"
```

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
