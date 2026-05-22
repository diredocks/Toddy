package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TaskInputBar(
  content: String,
  onContentChange: (String) -> Unit = {},
  onSubmit: () -> Unit = {},
  focusRequester: FocusRequester,
) {

  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    BasicTextField(
      value = content,
      onValueChange = onContentChange,
      modifier = Modifier
        .focusRequester(focusRequester)
        .weight(1.0f)
        .padding(horizontal = 24.dp),
      textStyle = MaterialTheme.typography.titleLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
      ),
      singleLine = true,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Send
      ),
      keyboardActions = KeyboardActions(
        onSend = { onSubmit() }
      ),
      // TODO: Same color as IconButton
      cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
      decorationBox = { innerTextField ->
        if (content.isBlank()) {
          Text(
            text = "New task",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
        innerTextField()
      }
    )
    IconButton(
      enabled = content != "",
      onClick = { onSubmit() },
      modifier = Modifier.padding(end = 12.dp)
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.Send,
        contentDescription = "Add"
      )
    }
  }
}