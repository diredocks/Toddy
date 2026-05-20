package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.clickfling.toddy.common.utils.toLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TodoItem(
  content: String,
  completed: Boolean,
  due: Long? = null,
  onCheckedChange: (Boolean) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val dateString = remember(due) {
    due?.let {
      val localDate = it.toLocalDate()
      val formatter = DateTimeFormatter.ofPattern(
        "E, MMM d",
        Locale.ENGLISH
      )
      localDate.format(formatter)
    }
  }

  ListItem(
    modifier = modifier,
    checked = completed,
    onCheckedChange = onCheckedChange,
    leadingContent = {
      Checkbox(
        checked = completed,
        onCheckedChange = onCheckedChange
      )
    },
    supportingContent = {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Tasks",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.labelSmall,
        )
        dateString?.let {
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .size(4.dp)
              .clip(CircleShape)
              .background(
                MaterialTheme.colorScheme
                  .onSurfaceVariant
                  .copy(alpha = 0.6f)
              )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(
            modifier = Modifier.size(10.dp),
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Date"
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = it,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
          )
        }
      }
    },
  ) {
    Text(
      text = content,
      style = if (completed) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
    )
  }
}

@Preview
@Composable
fun TodoItemPreview() {
  Column {
    TodoItem("Adam met Karl", false, 1716120000000L)
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Alice met Bob", true, 1716420000000L)
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Xiaoping met Elihu", true)
  }
}
