package top.clickfling.toddy.feature.todo.presentation.todos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.presentation.todos.components.AddTodoBottomSheet
import top.clickfling.toddy.feature.todo.presentation.todos.components.TodoItem

@Composable
fun TodosScreenRoute(
  navController: NavController,
  viewModel: TodosViewModel = hiltViewModel()
) {
  val state = viewModel.state

  TodosScreen(
    state = state,
    onAddButtonClick = {
      viewModel.onEvent(TodosEvent.ToggleSheetVisibility)
    },
    onSheetDismisRequest = {
      viewModel.onEvent(TodosEvent.ToggleSheetVisibility)
    },
    onContentChange = {
      viewModel.onEvent(TodosEvent.EnteredContent(it))
    },
    onSaveButtonClick = {
      viewModel.onEvent(TodosEvent.SaveTodo)
    },
    onItemCompletedChange = {
      viewModel.onEvent(TodosEvent.ToggleTodoCompleted(it))
    },
    onItemDelete = {
      viewModel.onEvent(TodosEvent.DeleteTodo(it))
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
  state: TodosState,
  onAddButtonClick: () -> Unit = {},
  onSaveButtonClick: () -> Unit = {},
  onSheetDismisRequest: () -> Unit = {},
  onContentChange: (String) -> Unit = {},
  onItemCompletedChange: (todo: Todo) -> Unit = {},
  onItemDelete: (todo: Todo) -> Unit = {},
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
        },
        scrollBehavior = scrollBehavior,
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = onAddButtonClick) {
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
        .fillMaxSize()
    ) {

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
      ) {
        item {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 4.dp),
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
        }

        items(state.todos, key = { it.id!! }) { todo ->
          TodoItem(
            content = todo.content,
            due = todo.due,
            completed = todo.completed,
            onCheckedChange = { onItemCompletedChange(todo) },
            onSwipeEndToStart = { onItemDelete(todo) },
            modifier = Modifier.animateItem()
          )
        }
      }
    }

    if (state.showSheet) {
      AddTodoBottomSheet(
        content = state.content,
        due = state.due,
        onDismissRequest = onSheetDismisRequest,
        onContentChange = onContentChange,
        onSaveClick = onSaveButtonClick,
      )
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
  )
}