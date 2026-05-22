package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RepeatChip() {
  InputChip(
    onClick = {},
    label = {
      Text("Repeat")
    },
    selected = false,
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Repeat,
        contentDescription = "Repeat",
        modifier = Modifier.size(InputChipDefaults.IconSize)
      )
    }
  )
}