package top.clickfling.toddy.feature.task.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

@Composable
fun DueSelectionMenu(
  onSelect: (TaskScheduleSelection) -> Unit,
  onCustomClick: () -> Unit,
) {
  DropdownMenuItem(
    text = { Text("Today") },
    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = "Today") },
    onClick = { onSelect(TaskScheduleSelection.Today) }
  )
  DropdownMenuItem(
    text = { Text("Tomorrow") },
    leadingIcon = { Icon(Icons.Default.ChevronRight, contentDescription = "Tomorrow") },
    onClick = { onSelect(TaskScheduleSelection.Tomorrow) }
  )
  DropdownMenuItem(
    text = { Text("Next week") },
    leadingIcon = {
      Icon(
        Icons.Default.KeyboardDoubleArrowRight,
        contentDescription = "Next week"
      )
    },
    onClick = { onSelect(TaskScheduleSelection.NextWeek) }
  )
  DropdownMenuItem(
    text = { Text("Pick a date") },
    leadingIcon = {
      Icon(
        Icons.Default.CalendarMonth,
        contentDescription = "Pick a date"
      )
    },
    onClick = onCustomClick
  )
}
