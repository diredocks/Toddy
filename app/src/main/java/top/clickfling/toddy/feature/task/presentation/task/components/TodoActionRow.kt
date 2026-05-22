package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TodoActionRow(
  icon: ImageVector,
  text: String,
  modifier: Modifier = Modifier,
  trailing: (@Composable () -> Unit)? = null,
  onClick: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(
      modifier = Modifier.padding(horizontal = 6.dp),
      onClick = onClick
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null
      )
    }

    Text(
      text = text,
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.weight(1f)
    )

    trailing?.invoke()
  }
}