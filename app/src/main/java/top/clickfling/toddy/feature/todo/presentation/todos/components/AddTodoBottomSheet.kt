package top.clickfling.toddy.feature.todo.presentation.todos.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
  onDismissRequest: () -> Unit = {},
  onSaveClick: () -> Unit = {}
) {
  val scrollState = rememberScrollState()

  ModalBottomSheet(
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
          value = "",
          onValueChange = {},
          modifier = Modifier
            .weight(1.0f)
            .padding(horizontal = 24.dp),
          textStyle = TextStyle(fontSize = 20.sp),
          singleLine = true,
          decorationBox = { innerTextField ->
            Text("New task", fontSize = 20.sp)
            innerTextField()
          }
        )
        IconButton(
          onClick = onSaveClick,
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
        InputChip(
          onClick = {},
          label = {
            Text("Set due date")
          },
          selected = false,
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.CalendarToday,
              contentDescription = "Due",
              modifier = Modifier.size(InputChipDefaults.IconSize)
            )
          }
        )
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
}

@Preview(heightDp = 250, widthDp = 450)
@Composable
fun AddTodoBottomSheetPreview() {
  AddTodoBottomSheet()
}