package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.presentation.common.components.DueSelectionMenu
import top.clickfling.toddy.feature.task.presentation.common.components.TaskDatePickerDialog
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

@Composable
fun DueActionRow(
  due: LocalDate?,
  onDueSelection: (TaskScheduleSelection) -> Unit,
) {
  val dueText = remember(due) {
    if (due != null) {
      val dayOfWeek = due.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = due.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "Due $dayOfWeek, $month ${due.day}"
    } else {
      "Set due date"
    }
  }
  var expanded by remember { mutableStateOf(false) }
  var showDatePicker by remember { mutableStateOf(false) }

  TodoActionRow(
    icon = Icons.Default.AccessTime,
    text = dueText,
    trailing = {
      if (due != null) {
        ClearButton(onClick = { onDueSelection(TaskScheduleSelection.Clear) })
      }
    },
    onClick = { expanded = true },
    textContent = { modifier ->
      Box(modifier = modifier) {
        Text(
          text = dueText,
          style = MaterialTheme.typography.bodyLarge,
        )
        DropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false },
          properties = PopupProperties(focusable = false)
        ) {
          DueSelectionMenu(
            onSelect = {
              expanded = false
              onDueSelection(it)
            },
            onCustomClick = {
              expanded = false
              showDatePicker = true
            }
          )
        }
      }
    }
  )

  if (showDatePicker) {
    TaskDatePickerDialog(
      initialDate = due,
      onDismissRequest = { showDatePicker = false },
      onConfirm = { onDueSelection(TaskScheduleSelection.Custom(it)) }
    )
  }
}
