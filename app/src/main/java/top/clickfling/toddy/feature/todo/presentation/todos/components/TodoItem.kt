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
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.math.abs
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TodoItem(
  content: String,
  completed: Boolean,
  important: Boolean,
  due: LocalDate? = null,
  remind: Instant? = null,
  modifier: Modifier = Modifier,
  onCheckedChange: (Boolean) -> Unit = {},
  onSwipeEndToStart: () -> Unit = {},
  onStarClicked: () -> Unit = {},
) {
  val dateString = remember(due) {
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
      selected = completed,
      onClick = {},
      leadingContent = { Checkbox(checked = completed, onCheckedChange = onCheckedChange) },
      trailingContent = {
        IconButton(
          onClick = onStarClicked,
          colors = IconButtonDefaults.iconButtonColors(
            contentColor = if (important)
              MaterialTheme.colorScheme.primary
            else
              MaterialTheme.colorScheme.onSurfaceVariant
          )
        ) {
          Icon(
            imageVector = if (important) Icons.Default.Star else Icons.Default.StarBorder,
            contentDescription = "Important"
          )
        }
      },
      supportingContent = {
        // TODO: Highlighting colors on special states
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Tasks",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
          )
          if (dateString == null) return@Row
          Box(
            modifier = Modifier
              .padding(horizontal = 5.dp)
              .size(3.dp)
              .clip(CircleShape)
              .background(
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
              )
          )
          Icon(
            modifier = Modifier.size(10.dp),
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Date"
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = dateString,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
          )
          if (remind == null) return@Row
          Box(
            modifier = Modifier
              .padding(horizontal = 5.dp)
              .size(3.dp)
              .clip(CircleShape)
              .background(
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
              )
          )
          Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Default.NotificationsNone,
            contentDescription = "Remind"
          )
        }
      },
    ) {
      Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(
          textDecoration = TextDecoration.LineThrough.takeIf { completed }
        )
      )
    }
  }
}

@Preview
@Composable
fun TodoItemPreview() {
  Column {
    TodoItem("Adam met Karl", false, true, LocalDate(2023, 1, 3))
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Alice met Bob", true, false, LocalDate(2023, 4, 5), Instant.fromEpochMilliseconds(0))
    Spacer(modifier = Modifier.height(8.dp))
    TodoItem("Xiaoping met Elihu", true, true)
  }
}
