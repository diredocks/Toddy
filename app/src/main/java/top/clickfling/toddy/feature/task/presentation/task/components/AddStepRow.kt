package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddStepRow(
  modifier: Modifier = Modifier,
  onSubmit: (String) -> Unit = {},
) {
  var text by remember { mutableStateOf("") }
  val focusManager = LocalFocusManager.current
  val isKeyboardVisible = WindowInsets.isImeVisible

  LaunchedEffect(isKeyboardVisible) {
    if (isKeyboardVisible) return@LaunchedEffect
    focusManager.clearFocus()
    if (text.isBlank()) {
      text = ""
    }
  }

  BasicTextField(
    value = text,
    onValueChange = { text = it },
    modifier = modifier
      .fillMaxWidth(),
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyLarge.copy(
      color = MaterialTheme.colorScheme.onSurface
    ),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(
      onDone = {
        val content = text.trim()
        if (content.isNotEmpty()) {
          onSubmit(content)
        }
        text = ""
        focusManager.clearFocus()
      }
    ),
    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
    decorationBox = { innerTextField ->
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
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

        Box {
          if (text.isEmpty()) {
            Text(
              text = "Add step",
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.primary
            )
          }
          innerTextField()
        }
      }
    }
  )
}
