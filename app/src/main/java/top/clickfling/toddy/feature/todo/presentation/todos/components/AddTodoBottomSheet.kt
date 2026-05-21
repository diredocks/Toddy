package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.todo.presentation.todos.util.DueSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
  content: String = "",
  due: Long? = null,
  onContentChange: (String) -> Unit = {},
  onDismissRequest: () -> Unit = {},
  onSaveClick: () -> Unit = {},
  onDueSelection: (DueSelection) -> Unit = {},
) {
  val scrollState = rememberScrollState()
  val sheetState = rememberBottomSheetState(SheetValue.Hidden)
  val scope = rememberCoroutineScope()
  val datePickerState = rememberDatePickerState()

  val dateString = remember(due) {
    due?.let {
      val localDate = LocalDate.fromEpochDays(it)

      val dayOfWeek = localDate.dayOfWeek.name.lowercase().replaceFirstChar { c -> c.uppercase() }
      val month = localDate.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }

      "$dayOfWeek, $month ${localDate.day}"
    }
  }

  var dueExpanded by remember { mutableStateOf(false) }
  var showDatePicker by remember { mutableStateOf(false) }

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
          cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
          decorationBox = { innerTextField ->
            if (content.isEmpty()) {
              Text("New task", fontSize = MaterialTheme.typography.titleLarge.fontSize)
            }
            innerTextField()
          }
        )
        IconButton(
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
        Box {
          InputChip(
            modifier = Modifier.animateContentSize(),
            onClick = { dueExpanded = true },
            label = {
              if (dateString != null) {
                Text("Due $dateString")
              } else {
                Text("Set due date")
              }
            },
            selected = due != null,
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Due",
                modifier = Modifier.size(InputChipDefaults.IconSize)
              )
            },
            trailingIcon = {
              if (due != null) {
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Clear due",
                  modifier = Modifier
                    .size(InputChipDefaults.IconSize)
                    .clickable(onClick = { onDueSelection(DueSelection.Clear) }),
                )
              }
            }
          )

          // TODO: Show dayOfWeek in menu items
          DropdownMenu(
            expanded = dueExpanded,
            onDismissRequest = { dueExpanded = false }
          ) {
            DropdownMenuItem(
              text = { Text("Today") },
              leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = "Today") },
              onClick = {
                dueExpanded = false
                onDueSelection(DueSelection.Today)
              }
            )
            DropdownMenuItem(
              text = { Text("Tomorrow") },
              leadingIcon = { Icon(Icons.Default.ChevronRight, contentDescription = "Tomorrow") },
              onClick = {
                dueExpanded = false
                onDueSelection(DueSelection.Tomorrow)
              }
            )
            DropdownMenuItem(
              text = { Text("Next week") },
              leadingIcon = {
                Icon(
                  Icons.Default.KeyboardDoubleArrowRight,
                  contentDescription = "Next week"
                )
              },
              onClick = {
                dueExpanded = false
                onDueSelection(DueSelection.NextWeek)
              }
            )
            DropdownMenuItem(
              text = { Text("Pick a date") },
              leadingIcon = {
                Icon(
                  Icons.Default.CalendarMonth,
                  contentDescription = "Pick a date"
                )
              },
              onClick = {
                dueExpanded = false
                showDatePicker = true
              }
            )
          }
        }
        InputChip(
          onClick = {},
          label = {
            Text("Remind me")
          },
          selected = false,
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.NotificationsNone,
              contentDescription = "Remind",
              modifier = Modifier.size(InputChipDefaults.IconSize)
            )
          }
        )
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
    }
  }

  if (showDatePicker) {
    DatePickerDialog(
      onDismissRequest = { showDatePicker = false },
      confirmButton = {
        TextButton(onClick = {
          onDueSelection(DueSelection.Custom(datePickerState.selectedDateMillis))
          showDatePicker = false
        }) {
          Text("OK")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDatePicker = false }) {
          Text("Cancel")
        }
      }
    ) {
      DatePicker(state = datePickerState)
    }
  }
}

@Preview(heightDp = 250, widthDp = 450)
@Composable
fun AddTodoBottomSheetPreview() {
  AddTodoBottomSheet()
}

@Preview(heightDp = 250, widthDp = 450)
@Composable
fun AddTodoBottomSheetWithContentPreview() {
  AddTodoBottomSheet(content = "Return books", due = 0L)
}