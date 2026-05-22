package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskItem(
  content: String,
  completed: Boolean,
  important: Boolean,
  due: LocalDate? = null,
  remind: Instant? = null,
  modifier: Modifier = Modifier,
  onCheckedChange: (Boolean) -> Unit = {},
  onSwipeEndToStart: () -> Unit = {},
  onStarClicked: () -> Unit = {},
  onItemClicked: () -> Unit = {},
) {
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
      SwipeDeleteBackground(
        dismissState = dismissState
      )
    }) {
    ListItem(
      selected = completed,
      onClick = onItemClicked,
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
        TaskMetaRow(
          due = due,
          remind = remind,
        )
      },
    ) {
      Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(
          textDecoration = TextDecoration.LineThrough.takeIf { completed }
        ),
        color = if (completed) MaterialTheme.colorScheme.onSurfaceVariant else Color.Unspecified
      )
    }
  }
}

@Preview
@Composable
fun TaskItemPreview() {
  Column {
    TaskItem("Adam met Karl", completed = false, important = true, due = LocalDate(2023, 1, 3))
    Spacer(modifier = Modifier.height(8.dp))
    TaskItem(
      "Alice met Bob",
      completed = true,
      important = false,
      due = LocalDate(2023, 4, 5),
      remind = Instant.fromEpochMilliseconds(0)
    )
    Spacer(modifier = Modifier.height(8.dp))
    TaskItem("Xiaoping met Elihu", completed = true, important = true)
  }
}
