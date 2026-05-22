package top.clickfling.toddy.feature.task.presentation.tasks.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.presentation.tasks.util.ChipSelection
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
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
  val focusRequester = remember { FocusRequester() }
  val submit = remember(content, scope, sheetState, onSaveClick) {
    {
      if (content.isNotBlank()) {
        scope.launch {
          sheetState.hide()
        }.invokeOnCompletion {
          if (!sheetState.isVisible) {
            onSaveClick()
          }
        }
      }
    }
  }

  LaunchedEffect(sheetState.currentValue) {
    if (sheetState.currentValue == SheetValue.Expanded) {
      focusRequester.requestFocus()
    }
  }

  ModalBottomSheet(
    sheetState = sheetState, onDismissRequest = onDismissRequest, dragHandle = {}) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 16.dp)
    ) {
      TaskInputBar(
        content = content,
        onContentChange = onContentChange,
        onSubmit = submit,
        focusRequester = focusRequester
      )
      ChipRow {
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
fun AddTaskBottomSheetPreview() {
  AddTaskBottomSheet()
}

@Preview(showSystemUi = true)
@Composable
fun AddTaskBottomSheetWithContentPreview() {
  AddTaskBottomSheet(content = "Return books", due = LocalDate(2024, 8, 2))
}