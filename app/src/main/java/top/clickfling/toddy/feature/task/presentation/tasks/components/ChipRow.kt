package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipRow(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit
) {
  val scrollState = rememberScrollState()

  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(scrollState)
      .padding(horizontal = 24.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    content = content
  )
}