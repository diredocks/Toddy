package top.clickfling.toddy.feature.task.presentation.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.result.LocalResultEventBus
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection
import top.clickfling.toddy.feature.task.presentation.task.components.AddStepRow
import top.clickfling.toddy.feature.task.presentation.task.components.DueActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.RemindActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.TodoActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.TodoCheckItem
import kotlin.time.Instant

@Composable
fun TaskScreenRoute(
  viewModel: TaskViewModel,
  onBackClick: () -> Unit,
) {
  val resultBus = LocalResultEventBus.current

  TaskScreen(
    state = viewModel.state,
    onNavIconClick = {
      onBackClick()
    },
    onDueSelection = {
      viewModel.onEvent(TaskEvent.SelectDue(it))
    },
    onRemindSelection = {
      viewModel.onEvent(TaskEvent.SelectRemind(it))
    },
    onCompletedChange = {
      viewModel.onEvent(TaskEvent.ToggleCompleted)
    },
    onImportanceClick = {
      viewModel.onEvent(TaskEvent.ToggleImportance)
    },
    onContentChange = {
      viewModel.onEvent(TaskEvent.OnContentChange(it))
    },
    onDeleteClick = {
      resultBus.sendResult(result = it)
      viewModel.onEvent(TaskEvent.DeleteTask)
      onBackClick()
    }
  )
}

@Composable
fun TaskScreen(
  state: TaskState,
  onNavIconClick: () -> Unit = {},
  onDueSelection: (TaskScheduleSelection) -> Unit = {},
  onRemindSelection: (TaskScheduleSelection) -> Unit = {},
  onCompletedChange: () -> Unit = {},
  onImportanceClick: () -> Unit = {},
  onContentChange: (String) -> Unit = {},
  onDeleteClick: (Task) -> Unit = {},
) {
  Scaffold(
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = onNavIconClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back"
            )
          }
        },
        actions = {
          IconButton(onClick = onImportanceClick) {
            Icon(
              imageVector = if (state.task.important) Icons.Default.Star else Icons.Default.StarBorder,
              contentDescription = if (state.task.important) "Remove star" else "Add star"
            )
          }
          IconButton(onClick = { onDeleteClick(state.task) }) {
            Icon(
              imageVector = Icons.Default.DeleteOutline,
              contentDescription = "Delete task"
            )
          }
        },
        title = {}
      )
    }
  ) {
    Column(
      modifier = Modifier.padding(it)
    ) {

      Column(
        modifier = Modifier.fillMaxWidth()
      ) {

        TodoCheckItem(
          text = state.task.content,
          checked = state.task.completed,
          style = MaterialTheme.typography.titleLarge,
          modifier = Modifier.padding(start = 4.dp, end = 6.dp),
          onCheckedChange = onCompletedChange,
          onContentChange = onContentChange
        )

        TodoCheckItem(
          text = "Let's go shopping",
          checked = true,
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.padding(start = 12.dp)
        )

        TodoCheckItem(
          text = "Then touch the grass",
          checked = false,
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.padding(start = 12.dp)
        )

        AddStepRow()
      }

      HorizontalDivider(Modifier.padding(vertical = 4.dp))

      RemindActionRow(
        remind = state.task.remind,
        onRemindSelection = onRemindSelection
      )

      DueActionRow(
        due = state.task.due,
        onDueSelection = onDueSelection
      )

      TodoActionRow(
        icon = Icons.Default.Repeat,
        text = "Repeat"
      )

      HorizontalDivider(Modifier.padding(vertical = 4.dp))

      TodoActionRow(
        icon = Icons.AutoMirrored.Filled.Notes,
        text = "Add notes"
      )

      HorizontalDivider(Modifier.padding(vertical = 4.dp))

      TodoActionRow(
        icon = Icons.Default.AttachFile,
        text = "Add attachments"
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun TaskScreenPreview() {
  TaskScreen(
    TaskState(
      Task(
        content = "Programming is hard",
        completed = true,
        important = true,
        due = LocalDate(2025, 7, 12),
        creation = Instant.fromEpochMilliseconds(0)
      )
    )
  )
}
