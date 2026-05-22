package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun SwipeDeleteBackground(
  dismissState: SwipeToDismissBoxState,
  modifier: Modifier = Modifier,
) {
  when (dismissState.dismissDirection) {
    SwipeToDismissBoxValue.EndToStart -> {
      BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterEnd
      ) {
        val density = LocalDensity.current

        val parentWidthPx = remember(maxWidth, density) {
          with(density) {
            maxWidth.toPx()
          }
        }

        val progress by remember {
          derivedStateOf {
            val offset = abs(dismissState.requireOffset())
            (offset / parentWidthPx)
              .coerceIn(0f, 1f)
          }
        }

        val backgroundColor = lerp(
          start = MaterialTheme.colorScheme.outlineVariant,
          stop = Color.Red,
          fraction = progress
        )

        Box(
          modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth(progress)
            .background(
              color = backgroundColor,
              shape = RoundedCornerShape(100.dp)
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete task",
            modifier = Modifier.padding(12.dp),
            tint = Color.White
          )
        }
      }
    }

    SwipeToDismissBoxValue.StartToEnd,
    SwipeToDismissBoxValue.Settled -> Unit
  }
}
