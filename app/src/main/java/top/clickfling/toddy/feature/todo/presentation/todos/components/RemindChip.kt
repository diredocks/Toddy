package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import top.clickfling.toddy.feature.todo.presentation.todos.util.ChipSelection
import kotlin.time.Clock
import kotlin.time.Instant

// TODO: Sync picker and remind?
@Composable
fun RemindChip(
  remind: Instant?,
  onRemindSelection: (ChipSelection) -> Unit = {},
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  var expanded by remember { mutableStateOf(false) }
  var showDatePicker by remember { mutableStateOf(false) }
  val datePickerState = rememberDatePickerState(
    initialSelectedDate = Clock.System.now()
      .toLocalDateTime(currentSystemTimeZone).date.toJavaLocalDate()
  )
  var showTimePicker by remember { mutableStateOf(false) }
  val timePickerState = rememberTimePickerState(
    initialHour = (Clock.System.now().toLocalDateTime(currentSystemTimeZone).hour + 1) % 24
  )

  val combinedSelectedInstant by remember {
    derivedStateOf {
      val dateMillis =
        datePickerState.selectedDateMillis
          ?: return@derivedStateOf null

      val localDate =
        Instant.fromEpochMilliseconds(dateMillis)
          .toLocalDateTime(TimeZone.UTC)
          .date

      LocalDateTime(
        year = localDate.year,
        month = localDate.month.number,
        day = localDate.day,
        hour = timePickerState.hour,
        minute = timePickerState.minute,
      ).toInstant(currentSystemTimeZone)
    }
  }

  val selectedTimeText = remember(timePickerState.hour, timePickerState.minute) {
    val hour = timePickerState.hour.toString().padStart(2, '0')
    val minute = timePickerState.minute.toString().padStart(2, '0')
    "$hour:$minute"
  }


  val chipText = remember(remind) {
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

  Box {
    InputChip(
      modifier = Modifier.animateContentSize(),
      onClick = { expanded = true },
      label = { Text(chipText) },
      selected = remind != null,
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.NotificationsNone,
          contentDescription = "Remind",
          modifier = Modifier.size(InputChipDefaults.IconSize)
        )
      },
      trailingIcon = {
        if (remind == null) return@InputChip
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Clear remind",
          modifier = Modifier
            .size(InputChipDefaults.IconSize)
            .clickable(onClick = { onRemindSelection(ChipSelection.Clear) }),
        )
      })
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      DropdownMenuItem(
        text = { Text("Later today") },
        leadingIcon = { Icon(Icons.Default.Timelapse, contentDescription = "Later today") },
        onClick = {
          expanded = false
          onRemindSelection(ChipSelection.Today)
        })
      DropdownMenuItem(
        text = { Text("Tomorrow") },
        leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = "Tomorrow") },
        onClick = {
          expanded = false
          onRemindSelection(ChipSelection.Tomorrow)
        })
      DropdownMenuItem(
        text = { Text("Next week") },
        leadingIcon = { Icon(Icons.Default.ArrowCircleUp, contentDescription = "Next week") },
        onClick = {
          expanded = false
          onRemindSelection(ChipSelection.NextWeek)
        })
      DropdownMenuItem(text = { Text("Pick a date & time") }, leadingIcon = {
        Icon(
          Icons.Default.CalendarMonth, contentDescription = "Custom date & time"
        )
      }, onClick = {
        expanded = false
        showDatePicker = true
      })
    }
  }

  if (showDatePicker) {
    DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
      TextButton(onClick = {
        combinedSelectedInstant?.let {
          onRemindSelection(
            ChipSelection.Custom(it)
          )
        }
        showDatePicker = false
      }) {
        Text("OK")
      }
    }, dismissButton = {
      TextButton(onClick = { showDatePicker = false }) {
        Text("Cancel")
      }
    }) {
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        DatePicker(
          state = datePickerState,
          title = null,
          headline = null,
          showModeToggle = false,
        )
        HorizontalDivider()
        Row(
          modifier = Modifier
            .clickable(onClick = { showTimePicker = true })
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 22.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = "Set time",
          )
          Spacer(modifier = Modifier.width(20.dp))
          SuggestionChip(
            onClick = { showTimePicker = true },
            label = { Text(selectedTimeText) }
          )
        }
      }
    }
  }

  if (showTimePicker) {
    TimePickerDialog(
      onDismissRequest = { showTimePicker = false },
      confirmButton = {
        TextButton(onClick = {
          showTimePicker = false
        }) {
          Text("OK")
        }
      },
      dismissButton = {
        TextButton(onClick = { showTimePicker = false }) {
          Text("Cancel")
        }
      },
      title = {},
    ) {
      TimePicker(state = timePickerState)
    }
  }
}