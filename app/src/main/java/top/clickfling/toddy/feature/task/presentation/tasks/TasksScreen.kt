package top.clickfling.toddy.feature.task.presentation.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.presentation.tasks.components.AddTaskBottomSheet
import top.clickfling.toddy.feature.task.presentation.tasks.components.TaskItem
import top.clickfling.toddy.feature.task.presentation.tasks.util.ChipSelection
import kotlin.time.Instant

@Composable
fun TasksScreenRoute(
  navController: NavController,
  viewModel: TasksViewModel = hiltViewModel()
) {
  val state = viewModel.state

  TasksScreen(
    state = state,
    onAddButtonClick = {
      viewModel.onEvent(TasksEvents.ToggleSheetVisibility)
    },
    onSheetDismissRequest = {
      viewModel.onEvent(TasksEvents.ToggleSheetVisibility)
    },
    onContentChange = {
      viewModel.onEvent(TasksEvents.EnteredContent(it))
    },
    onSaveButtonClick = {
      viewModel.onEvent(TasksEvents.SaveTask)
    },
    onItemCompletedChange = {
      viewModel.onEvent(TasksEvents.ToggleCompleted(it))
    },
    onItemDelete = {
      viewModel.onEvent(TasksEvents.DeleteTask(it))
    },
    onItemRestore = {
      viewModel.onEvent(TasksEvents.RestoreTask)
    },
    onDueSelection = {
      viewModel.onEvent(TasksEvents.SelectDue(it))
    },
    onRemindSelection = {
      viewModel.onEvent(TasksEvents.SelectRemind(it))
    },
    onItemImportanceChange = {
      viewModel.onEvent(TasksEvents.ToggleImportance(it))
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
  state: TasksState,
  onAddButtonClick: () -> Unit = {},
  onSaveButtonClick: () -> Unit = {},
  onSheetDismissRequest: () -> Unit = {},
  onContentChange: (String) -> Unit = {},
  onItemCompletedChange: (task: Task) -> Unit = {},
  onItemDelete: (task: Task) -> Unit = {},
  onItemRestore: () -> Unit = {},
  onDueSelection: (ChipSelection) -> Unit = {},
  onRemindSelection: (ChipSelection) -> Unit = {},
  onItemImportanceChange: (task: Task) -> Unit = {},
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  val scope = rememberCoroutineScope()
  var snackbarJob by remember { mutableStateOf<Job?>(null) }

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MediumTopAppBar(
        title = {
          Text("Planned")
        },
        navigationIcon = {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back"
            )
          }
        },
        actions = {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.Default.MoreVert, contentDescription = "Menu"
            )
          }
        },
        scrollBehavior = scrollBehavior,
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = onAddButtonClick) {
        Icon(
          imageVector = Icons.Default.Add, contentDescription = "Add task"
        )
      }
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
  ) {
    Column(
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
    ) {

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
      ) {
        item {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 4.dp),
          ) {
            FilterChip(
              onClick = {},
              label = {
                Text("All Planned")
              },
              selected = state.beforeTimestamp != null,
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Menu,
                  contentDescription = "Filter",
                  modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
              }
            )
          }
        }

        items(state.tasks, key = { it.id!! }) { task ->
          TaskItem(
            content = task.content,
            due = task.due,
            important = task.important,
            remind = task.remind,
            completed = task.completed,
            modifier = Modifier.animateItem(),
            onCheckedChange = { onItemCompletedChange(task) },
            onSwipeEndToStart = {
              onItemDelete(task)
              snackbarJob?.cancel()
              snackbarJob = scope.launchDeleteSnackbar(
                snackbarHostState = snackbarHostState,
                onRestore = onItemRestore
              )
            },
            onStarClicked = { onItemImportanceChange(task) }
          )
        }
      }
    }

    if (state.showSheet) {
      AddTaskBottomSheet(
        content = state.content,
        due = state.due,
        remind = state.remind,
        onDismissRequest = onSheetDismissRequest,
        onContentChange = onContentChange,
        onSaveClick = onSaveButtonClick,
        onDueSelection = onDueSelection,
        onRemindSelection = onRemindSelection,
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun TasksScreenPreview() {
  TasksScreen(
    state = TasksState(
      tasks = listOf(
        Task(
          id = 0,
          completed = false,
          important = false,
          content = "Programming is hard",
          creation = Instant.fromEpochMilliseconds(0)
        ),
        Task(
          id = 1,
          completed = true,
          important = false,
          content = "Let's go shopping",
          creation = Instant.fromEpochMilliseconds(0),
          due = LocalDate(2025, 6, 1)
        ),
      )
    ),
  )
}

private fun CoroutineScope.launchDeleteSnackbar(
  snackbarHostState: SnackbarHostState,
  onRestore: () -> Unit,
): Job = launch {
  snackbarHostState.currentSnackbarData?.dismiss()

  val result = snackbarHostState.showSnackbar(
    message = "Task deleted",
    actionLabel = "Undo",
    duration = SnackbarDuration.Short
  )

  if (result == SnackbarResult.ActionPerformed) {
    onRestore()
  }
}