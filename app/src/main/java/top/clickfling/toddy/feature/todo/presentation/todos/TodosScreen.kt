package top.clickfling.toddy.feature.todo.presentation.todos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.presentation.todos.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
  state: TodosState,
  snackbarHostState: SnackbarHostState,
) {
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = {
          Text("Planned")
        },
        navigationIcon = {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back"
            )
          }
        },
        actions = {
          IconButton(onClick = {}) {
            Icon(
              imageVector = Icons.Default.MoreVert, contentDescription = "Menu"
            )
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {}) {
        Icon(
          imageVector = Icons.Default.Add, contentDescription = "Add todo"
        )
      }
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
  ) {
    Column(
      modifier = Modifier
        .padding(it)
        .padding(start = 16.dp, end = 16.dp)
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          onClick = {},
          label = {
            Text("All Planned")
          },
          selected = state.beforeTimestamp != null,
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = "Filter",
              modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
          }
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(state.todos) { todo ->
          TodoItem(
            content = todo.content,
            due = todo.due,
            completed = todo.completed
          )
        }
      }
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun TodosScreenPreview() {
  TodosScreen(
    state = TodosState(
      todos = listOf(
        Todo(
          false,
          "blade bird",
          0,
          1716120000000L,
        ),
        Todo(
          true,
          "choke enough",
          0,
          1716129000000L,
        ),
      )
    ),
    snackbarHostState = SnackbarHostState()
  )
}