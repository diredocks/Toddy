package top.clickfling.toddy.feature.task.presentation.task.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TodoCheckItem(
  text: String,
  checked: Boolean,
  style: TextStyle,
  modifier: Modifier = Modifier,
  onCheckedChange: () -> Unit = {},
  onContentChange: (String) -> Unit = {},
) {
  val focusManager = LocalFocusManager.current
  val isKeyboardVisible = WindowInsets.isImeVisible

  LaunchedEffect(isKeyboardVisible) {
    if (isKeyboardVisible) return@LaunchedEffect
    focusManager.clearFocus()
  }

  val textColor =
    if (checked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
  val itemStyle = style.copy(
    color = textColor,
    textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
  )

  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.fillMaxWidth()
  ) {
    Checkbox(
      checked = checked,
      onCheckedChange = { onCheckedChange() }
    )

    BasicTextField(
      value = text,
      onValueChange = { onContentChange(it) },
      modifier = Modifier.weight(1f),
      textStyle = itemStyle,
      singleLine = true,
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
      cursorBrush = SolidColor(textColor)
    )
  }
}