package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.todo.presentation.todos.util.ChipSelection
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
  content: String = "",
  due: LocalDate? = null,
  remind: Instant? = null,
  onContentChange: (String) -> Unit = {},
  onDismissRequest: () -> Unit = {},
  onSaveClick: () -> Unit = {},
  onDueSelection: (ChipSelection) -> Unit = {},
  onRemindSelection: (ChipSelection) -> Unit = {},
) {
  val scrollState = rememberScrollState()
  val sheetState = rememberBottomSheetState(SheetValue.Hidden)
  val scope = rememberCoroutineScope()

  ModalBottomSheet(
    sheetState = sheetState,
    onDismissRequest = onDismissRequest,
    dragHandle = {}
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        BasicTextField(
          value = content,
          onValueChange = onContentChange,
          modifier = Modifier
            .weight(1.0f)
            .padding(horizontal = 24.dp),
          textStyle = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
          ),
          singleLine = true,
          // TODO: Same color as IconButton
          cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
          decorationBox = { innerTextField ->
            if (content.isEmpty()) {
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
          onClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
              if (!sheetState.isVisible) {
                onSaveClick()
              }
            }
          },
          modifier = Modifier.padding(end = 12.dp)
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Add"
          )
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(scrollState)
          .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        ListChip()
        DueChip(due, onDueSelection)
        RemindChip(remind, onRemindSelection)
        RepeatChip()
      }
    }
  }
}

@Preview(heightDp = 250, widthDp = 450)
@Composable
fun AddTodoBottomSheetPreview() {
  AddTodoBottomSheet()
}

@Preview(showSystemUi = true)
@Composable
fun AddTodoBottomSheetWithContentPreview() {
  AddTodoBottomSheet(content = "Return books", due = LocalDate(2024, 8, 2))
}