package top.clickfling.toddy.feature.task.presentation.common.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun TaskDatePickerDialog(
  initialDate: LocalDate?,
  onDismissRequest: () -> Unit,
  onConfirm: (Instant) -> Unit,
) {
  val currentSystemTimeZone = remember { TimeZone.currentSystemDefault() }
  val datePickerState = rememberDatePickerState(
    initialSelectedDate = (initialDate ?: Clock.System.now()
      .toLocalDateTime(currentSystemTimeZone).date)
      .toJavaLocalDate()
  )

  DatePickerDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = {
      TextButton(
        onClick = {
          datePickerState.selectedDateMillis
            ?.let(Instant::fromEpochMilliseconds)
            ?.let(onConfirm)
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
