package top.clickfling.toddy.feature.todo.presentation.todos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.useCase.TodoUseCases
import top.clickfling.toddy.feature.todo.domain.util.TodoOrder
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
  private val todoUseCases: TodoUseCases
): ViewModel() {
  var state by mutableStateOf(TodosState())
    private set

  private var recentlyDeletedTodo: Todo? = null
  private var getTodosJob: Job? = null

  init {
    getTodos(state.todoOrder)
  }

  fun onEvent(event: TodosEvent) {
    when(event) {
      is TodosEvent.DeleteTodo -> {
        viewModelScope.launch {
          todoUseCases.deleteTodo(event.todo)
          recentlyDeletedTodo = event.todo
        }
      }

      is TodosEvent.Order -> {
        if (state.todoOrder == event.todoOrder) return
        state = state.copy(todoOrder = event.todoOrder)
      }

      TodosEvent.RestoreTodo -> {
        viewModelScope.launch {
          todoUseCases.addTodo(recentlyDeletedTodo ?: return@launch)
          recentlyDeletedTodo = null
        }
      }

      TodosEvent.ToggleCompletedVisibility -> {
        state = state.copy(showCompleted = !state.showCompleted)
      }

      TodosEvent.ToggleSheetVisibility -> {
        state = state.copy(showSheet = !state.showSheet)
      }
    }
  }

  private fun getTodos(order: TodoOrder) {
    getTodosJob?.cancel()
    getTodosJob = todoUseCases.getTodos(order)
      .onEach { todos ->
        state = state.copy(
          todos = todos,
          todoOrder = order
        )
      }
      .launchIn(viewModelScope)
  }
}