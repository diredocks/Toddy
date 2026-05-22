package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import top.clickfling.toddy.feature.task.presentation.common.components.RemindSelectionMenu
import top.clickfling.toddy.feature.task.presentation.common.components.TaskDateTimePickerDialog
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection
import kotlin.time.Instant

@Composable
fun RemindActionRow(
  remind: Instant?,
  onRemindSelection: (TaskScheduleSelection) -> Unit,
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  val remindText = remember(remind) {
    if (remind != null) {
      val localDateTime = remind.toLocalDateTime(currentSystemTimeZone)
      val dayOfWeek =
        localDateTime.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = localDateTime.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "Remind me at ${localDateTime.time}, $dayOfWeek, $month ${localDateTime.day}"
    } else {
      "Remind me"
    }
  }
  var expanded by remember { mutableStateOf(false) }
  var showDateTimePicker by remember { mutableStateOf(false) }

  Box(modifier = Modifier.fillMaxWidth()) {
    TodoActionRow(
      icon = Icons.Default.NotificationsNone,
      text = remindText,
      trailing = {
        if (remind != null) {
          ClearButton(onClick = { onRemindSelection(TaskScheduleSelection.Clear) })
        }
      },
      onClick = { expanded = true }
    )

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      properties = PopupProperties(focusable = false)
    ) {
      RemindSelectionMenu(
        onSelect = {
          expanded = false
          onRemindSelection(it)
        },
        onCustomClick = {
          expanded = false
          showDateTimePicker = true
        }
      )
    }
  }

  if (showDateTimePicker) {
    TaskDateTimePickerDialog(
      initialReminder = remind,
      onDismissRequest = { showDateTimePicker = false },
      onConfirm = { onRemindSelection(TaskScheduleSelection.Custom(it)) }
    )
  }
}
