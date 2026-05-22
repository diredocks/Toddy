package top.clickfling.toddy.feature.task.presentation.task

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.presentation.task.components.AddStepRow
import top.clickfling.toddy.feature.task.presentation.task.components.DueActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.RemindActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.TodoActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.TodoCheckItem
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

@Composable
fun TaskScreenRoute(
  viewModel: TaskViewModel,
  onBackClick: () -> Unit,
) {
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
    onImportanceClick = {
      viewModel.onEvent(TaskEvent.ToggleImportance)
    },
    onDeleteClick = {
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
  onImportanceClick: () -> Unit = {},
  onDeleteClick: () -> Unit = {},
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
              imageVector = if (state.important) Icons.Default.Star else Icons.Default.StarBorder,
              contentDescription = if (state.important) "Remove star" else "Add star"
            )
          }
          IconButton(onClick = onDeleteClick) {
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
          text = state.content,
          checked = state.completed,
          style = MaterialTheme.typography.titleLarge,
          modifier = Modifier.padding(start = 4.dp)
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
        remind = state.remind,
        onRemindSelection = onRemindSelection
      )

      DueActionRow(
        due = state.due,
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
      content = "Programming is hard",
      completed = true,
      important = true,
      due = LocalDate(2025, 7, 12)
    )
  )
}
