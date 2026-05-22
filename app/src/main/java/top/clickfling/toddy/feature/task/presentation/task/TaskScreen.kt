package top.clickfling.toddy.feature.task.presentation.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import top.clickfling.toddy.feature.task.presentation.task.components.AddStepRow
import top.clickfling.toddy.feature.task.presentation.task.components.ClearButton
import top.clickfling.toddy.feature.task.presentation.task.components.TodoActionRow
import top.clickfling.toddy.feature.task.presentation.task.components.TodoCheckItem

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
  )
}

@Composable
fun TaskScreen(
  state: TaskState,
  onNavIconClick: () -> Unit = {},
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  val remindText = remember(state.remind) {
    if (state.remind != null) {
      val localDateTime = state.remind.toLocalDateTime(currentSystemTimeZone)

      val dayOfWeek =
        localDateTime.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = localDateTime.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "Remind me at ${localDateTime.time}, $dayOfWeek, $month ${localDateTime.day}"
    } else {
      "Remind me"
    }
  }
  val dueString = remember(state.due) {
    if (state.due != null) {
      val dayOfWeek = state.due.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = state.due.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "Due $dayOfWeek, $month ${state.due.day}"
    } else {
      "Set due date"
    }
  }

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

      TodoActionRow(
        icon = Icons.Default.NotificationsNone,
        text = remindText,
        trailing = {
          if (state.remind != null) {
            ClearButton()
          }
        }
      )

      TodoActionRow(
        icon = Icons.Default.AccessTime,
        text = dueString,
        trailing = {
          if (state.due != null) {
            ClearButton()
          }
        }
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
      due = LocalDate(2025, 7, 12)
    )
  )
}
