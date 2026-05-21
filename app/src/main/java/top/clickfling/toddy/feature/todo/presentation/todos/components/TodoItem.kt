package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.math.abs

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TodoItem(
  content: String,
  completed: Boolean,
  due: Long? = null,
  onCheckedChange: (Boolean) -> Unit = {},
  onSwipeEndToStart: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val dateString = remember(due) {
    due?.let {
      val localDate = LocalDate.fromEpochDays(it)
      val dayOfWeek = localDate.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = localDate.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      "$dayOfWeek, $month ${localDate.day}"
    }
  }
  val dismissState = rememberSwipeToDismissBoxState()
  val scope = rememberCoroutineScope()

  SwipeToDismissBox(
    modifier = modifier,
    state = dismissState,
    enableDismissFromStartToEnd = false,
    onDismiss = { dismissValue ->
      when (dismissValue) {
        SwipeToDismissBoxValue.EndToStart -> {
          scope.launch {
            dismissState.snapTo(SwipeToDismissBoxValue.StartToEnd)
            onSwipeEndToStart()
            dismissState.reset()
          }
        }

        SwipeToDismissBoxValue.StartToEnd -> {}
        SwipeToDismissBoxValue.Settled -> {}
      }
    },
    backgroundContent = {
      when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
          BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
          ) {
            val density = LocalDensity.current
            val parentWidthPx = with(density) {
              maxWidth.toPx()
            }
            val progress by remember {
              derivedStateOf {
                val offset = abs(dismissState.requireOffset())
                (offset / parentWidthPx).coerceIn(0f, 1f)
              }
            }

            Box(
              modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(
                  color = lerp(MaterialTheme.colorScheme.outlineVariant, Color.Red, progress),
                  shape = RoundedCornerShape(100.dp)
                )
            ) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete todo",
                modifier = Modifier
                  .fillMaxSize()
                  .wrapContentSize(Alignment.Center)
                  .padding(12.dp),
                tint = Color.White
              )
            }
          }
        }

        SwipeToDismissBoxValue.StartToEnd -> {}
        SwipeToDismissBoxValue.Settled -> {}
      }
    }) {
    ListItem(
      checked = completed,
      onCheckedChange = onCheckedChange,
      leadingContent = {
        Checkbox(
          checked = completed, onCheckedChange = onCheckedChange
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
                  MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
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
}

@Preview
@Composable
fun TodoItemPreview() {
  Column {
    TodoItem("Adam met Karl", false, 171612000L)
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Alice met Bob", true, 171642000L)
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Xiaoping met Elihu", true)
  }
}
