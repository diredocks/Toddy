package top.clickfling.toddy.feature.task.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

@Composable
fun RemindSelectionMenu(
  onSelect: (TaskScheduleSelection) -> Unit,
  onCustomClick: () -> Unit,
) {
  DropdownMenuItem(
    text = { Text("Later today") },
    leadingIcon = { Icon(Icons.Default.Timelapse, contentDescription = "Later today") },
    onClick = { onSelect(TaskScheduleSelection.Today) }
  )
  DropdownMenuItem(
    text = { Text("Tomorrow") },
    leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = "Tomorrow") },
    onClick = { onSelect(TaskScheduleSelection.Tomorrow) }
  )
  DropdownMenuItem(
    text = { Text("Next week") },
    leadingIcon = { Icon(Icons.Default.ArrowCircleUp, contentDescription = "Next week") },
    onClick = { onSelect(TaskScheduleSelection.NextWeek) }
  )
  DropdownMenuItem(
    text = { Text("Pick a date & time") },
    leadingIcon = {
      Icon(
        Icons.Default.CalendarMonth,
        contentDescription = "Custom date & time"
      )
    },
    onClick = onCustomClick
  )
}
