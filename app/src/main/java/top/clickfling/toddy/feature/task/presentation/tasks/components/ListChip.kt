package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListChip() {
  InputChip(
    onClick = {},
    label = {
      Text("Tasks")
    },
    selected = false,
    leadingIcon = {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.List,
        contentDescription = "List",
        modifier = Modifier.size(InputChipDefaults.IconSize)
      )
    }
  )
}