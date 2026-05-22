package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

@Composable
fun TaskMetaRow(
  due: LocalDate?,
  remind: Instant?,
) {
  val dueString = remember(due) {
    due?.let { localDate ->
      val dayOfWeek =
        localDate.dayOfWeek.name.lowercase()
          .replaceFirstChar { c -> c.uppercase() }

      val month =
        localDate.month.name.lowercase()
          .replaceFirstChar { c -> c.uppercase() }

      "$dayOfWeek, $month ${localDate.day}"
    }
  }

  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Tasks",
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    dueString?.let {
      TaskMetaDivider()

      TaskMetaItem(
        icon = Icons.Default.CalendarToday,
        text = it
      )
    }

    remind?.let {
      TaskMetaDivider()

      TaskMetaItem(
        icon = Icons.Default.NotificationsNone,
      )
    }
  }
}