package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TaskMetaItem(
  icon: ImageVector,
  text: String? = null
) {
  Icon(
    imageVector = icon,
    contentDescription = null,
    modifier = Modifier.size(12.dp)
  )

  if (text != null) {
    Spacer(Modifier.width(4.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}