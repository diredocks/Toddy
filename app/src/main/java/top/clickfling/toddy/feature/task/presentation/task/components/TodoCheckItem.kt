package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun TodoCheckItem(
  text: String,
  checked: Boolean,
  style: TextStyle,
  modifier: Modifier = Modifier,
  onCheckedChange: () -> Unit = {},
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth()
  ) {
    Checkbox(
      checked = checked,
      onCheckedChange = { onCheckedChange() }
    )

    Text(
      text = text,
      style = style.copy(
        textDecoration = TextDecoration.LineThrough.takeIf { checked }
      ),
      color = if (checked) {
        MaterialTheme.colorScheme.onSurfaceVariant
      } else {
        Color.Unspecified
      }
    )
  }
}
