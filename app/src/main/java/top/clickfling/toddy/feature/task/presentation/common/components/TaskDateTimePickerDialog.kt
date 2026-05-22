package top.clickfling.toddy.feature.task.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun TaskDateTimePickerDialog(
  initialReminder: Instant?,
  onDismissRequest: () -> Unit,
  onConfirm: (Instant) -> Unit,
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  val initialDateTime = remember(initialReminder) {
    initialReminder?.toLocalDateTime(currentSystemTimeZone)
      ?: Clock.System.now().toLocalDateTime(currentSystemTimeZone)
  }
  val datePickerState = rememberDatePickerState(
    initialSelectedDate = initialDateTime.date.toJavaLocalDate()
  )
  val timePickerState = rememberTimePickerState(
    initialHour = initialDateTime.hour,
    initialMinute = initialDateTime.minute,
  )
  var showTimePicker by remember { mutableStateOf(false) }

  val combinedSelectedInstant by remember {
    derivedStateOf {
      val dateMillis = datePickerState.selectedDateMillis ?: return@derivedStateOf null
      val localDate = Instant.fromEpochMilliseconds(dateMillis).toLocalDateTime(TimeZone.UTC).date

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
    "${timePickerState.hour.toString().padStart(2, '0')}:${timePickerState.minute.toString().padStart(2, '0')}"
  }

  DatePickerDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = {
      TextButton(
        onClick = {
          combinedSelectedInstant?.let(onConfirm)
          onDismissRequest()
        }
      ) {
        Text("OK")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismissRequest) {
        Text("Cancel")
      }
    }
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
      )
      HorizontalDivider()
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = { showTimePicker = true })
          .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = { showTimePicker = true }) {
          Icon(
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = "Set time",
          )
        }
        Text(
          text = selectedTimeText,
          style = MaterialTheme.typography.bodyMedium
        )
      }
      HorizontalDivider()
    }
  }

  if (showTimePicker) {
    TimePickerDialog(
      onDismissRequest = { showTimePicker = false },
      confirmButton = {
        TextButton(onClick = { showTimePicker = false }) {
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
