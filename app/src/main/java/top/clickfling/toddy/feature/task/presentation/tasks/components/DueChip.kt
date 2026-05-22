package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import top.clickfling.toddy.feature.task.presentation.tasks.util.ChipSelection
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun DueChip(
  due: LocalDate?,
  onDueSelection: (ChipSelection) -> Unit = {},
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  val dueString = remember(due) {
    if (due != null) {
      val dayOfWeek = due.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = due.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "$dayOfWeek, $month ${due.day}"
    } else {
      "Set due date"
    }
  }
  var expanded by remember { mutableStateOf(false) }
  var showDatePicker by remember { mutableStateOf(false) }
  val datePickerState = rememberDatePickerState(
    initialSelectedDate = Clock.System.now()
      .toLocalDateTime(currentSystemTimeZone).date.toJavaLocalDate()
  )

  Box {
    InputChip(
      modifier = Modifier.animateContentSize(),
      onClick = { expanded = !expanded },
      label = { Text(dueString) },
      selected = due != null,
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.CalendarToday,
          contentDescription = "Due",
          modifier = Modifier.size(InputChipDefaults.IconSize)
        )
      },
      trailingIcon = {
        if (due == null) return@InputChip
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Clear due",
          modifier = Modifier
            .size(InputChipDefaults.IconSize)
            .clickable(onClick = { onDueSelection(ChipSelection.Clear) }),
        )
      }
    )

    // TODO: Show dayOfWeek in menu items
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      properties = PopupProperties(focusable = false)
    ) {
      DropdownMenuItem(
        text = { Text("Today") },
        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = "Today") },
        onClick = {
          expanded = false
          onDueSelection(ChipSelection.Today)
        }
      )
      DropdownMenuItem(
        text = { Text("Tomorrow") },
        leadingIcon = { Icon(Icons.Default.ChevronRight, contentDescription = "Tomorrow") },
        onClick = {
          expanded = false
          onDueSelection(ChipSelection.Tomorrow)
        }
      )
      DropdownMenuItem(
        text = { Text("Next week") },
        leadingIcon = {
          Icon(
            Icons.Default.KeyboardDoubleArrowRight,
            contentDescription = "Next week"
          )
        },
        onClick = {
          expanded = false
          onDueSelection(ChipSelection.NextWeek)
        }
      )
      DropdownMenuItem(
        text = { Text("Pick a date") },
        leadingIcon = {
          Icon(
            Icons.Default.CalendarMonth,
            contentDescription = "Pick a date"
          )
        },
        onClick = {
          expanded = false
          showDatePicker = true
        }
      )
    }
  }

  if (showDatePicker) {
    DatePickerDialog(
      onDismissRequest = { showDatePicker = false },
      confirmButton = {
        TextButton(onClick = {
          datePickerState.selectedDateMillis
            ?.let(Instant::fromEpochMilliseconds)
            ?.let {
              onDueSelection(ChipSelection.Custom(it))
            }
          showDatePicker = false
        }) {
          Text("OK")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDatePicker = false }) {
          Text("Cancel")
        }
      },

      ) {
      DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
      )
    }
  }
}