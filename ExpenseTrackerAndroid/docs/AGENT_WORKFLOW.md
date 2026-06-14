# Agent Workflow

How AI agents should work on this repository.

## Subagent matrix

| Task | Tool |
|------|------|
| Explore codebase, find patterns | Task `explore` (medium) |
| Gradle build, test, adb, git | Task `shell` |
| Review local/branch diff | Task `bugbot` |
| Security review before release | Task `security-review` |
| Implement feature | Main agent + project skill |

## Global skills

| Skill | When |
|-------|------|
| `android-jetpack-compose-expert` | Compose UI, navigation, Material 3 |
| `kotlin-coroutines-expert` | Flow, ViewModel coroutines |
| `android_ui_verification` | Emulator UI verify via ADB |
| `test-driven-development` | New feature — tests first |
| `find-bugs` / `review-bugbot` | Pre-merge review |
| `commit` / `create-pr` | Git workflow |
| `lint-and-validate` | Run build/test after changes |
| `plan-writing` | Large feature planning |

## Project skill

Read `.cursor/skills/expense-tracker-android/SKILL.md` before implementing features.

## Reusable prompts

### Add feature

```
Theo docs/ADDING_FEATURE.md, thêm feature "{name}" với Entity, Repository,
ViewModel, Screen, Route. Extend BaseViewModel và BaseRepository.
Dùng StateHandler. Strings tiếng Việt.
```

### Add Compose screen

```
Tạo màn {name}: ViewModel extends BaseViewModel, UiState<T>, StateHandler,
hiltViewModel(). Không business logic trong Composable.
```

Hoặc chạy script (chỉ presentation layer): `./scripts/new_screen.sh {name}` — xem [ADDING_FEATURE.md](ADDING_FEATURE.md#scaffold-screen-presentation-only).

### Review branch

```
Bugbot review branch changes. Focus MVVM violations: DAO in ViewModel,
logic in Composable, missing UiState pattern.
```

### Verify build

```
Run ./gradlew assembleDebug && ./gradlew testDebugUnitTest.
Fix any failures.
```

## Doc links

- [AGENTS.md](../AGENTS.md)
- [ADDING_FEATURE.md](ADDING_FEATURE.md)
- [ARCHITECTURE.md](ARCHITECTURE.md)
