package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import top.clickfling.toddy.common.utils.toLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TodoItem(
  content: String,
  due: Long,
  completed: Boolean,
) {
  val dateString = remember(due) {
    val localDate = due.toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("E, MMM d", Locale.ENGLISH)
    localDate.format(formatter)
  }

  ListItem(
    checked = completed,
    onCheckedChange = {},
    leadingContent = {
      Checkbox(
        checked = completed,
        onCheckedChange = {}
      )
    },
    supportingContent = {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Tasks")
        Spacer(modifier = Modifier.width(8.dp))
        Box(
          modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          modifier = Modifier.size(12.dp),
          imageVector = Icons.Default.CalendarToday,
          contentDescription = "Date"
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(dateString)
      }
    },
    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 10.dp)
  ) {
    Text(
      text = content,
    )
  }
}
