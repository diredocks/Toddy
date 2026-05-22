package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddStepRow(
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .defaultMinSize(minHeight = 48.dp)
  ) {
    Box(
      modifier = Modifier
        .padding(start = 12.dp)
        .size(48.dp),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
      )
    }

    Text(
      text = "Add step",
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.primary
    )
  }
}