package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClearButton(
  onClick: () -> Unit = {},
) {
  IconButton(
    modifier = Modifier.padding(horizontal = 6.dp),
    onClick = onClick
  ) {
    Icon(
      imageVector = Icons.Default.Close,
      contentDescription = "Cancel"
    )
  }
}