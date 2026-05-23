package top.clickfling.toddy.feature.task.presentation.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.clickfling.toddy.feature.task.domain.model.InvalidTaskException
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import top.clickfling.toddy.feature.task.presentation.common.util.toDueDays
import top.clickfling.toddy.feature.task.presentation.common.util.toRemindTime
import top.clickfling.toddy.feature.task.presentation.util.Screen

@HiltViewModel(assistedFactory = TaskViewModel.Factory::class)
class TaskViewModel @AssistedInject constructor(
  private val taskUseCases: TaskUseCases,
  @Assisted private val key: Screen.Task,
) : ViewModel() {
  var state by mutableStateOf(TaskState())
    private set

  init {
    loadTask(key.taskId)
  }

  fun onEvent(event: TaskEvent) {
    when (event) {
      TaskEvent.DeleteTask -> {
        deleteCurrentTask()
      }

      TaskEvent.ToggleCompleted -> {
        updateTask { it.copy(completed = !it.completed) }
      }

      is TaskEvent.SelectDue -> {
        val due = event.dueSelection.toDueDays()
        updateTask { it.copy(due = due) }
      }

      is TaskEvent.SelectRemind -> {
        val remind = event.remindSelection.toRemindTime()
        updateTask { it.copy(remind = remind) }
      }

      TaskEvent.ToggleImportance -> {
        updateTask { it.copy(important = !it.important) }
      }

      is TaskEvent.OnContentChange -> {
        if (event.content.isNotBlank()) {
          updateTask { it.copy(content = event.content) }
        }
      }
    }
  }

  private fun loadTask(taskId: Int?) {
    if (taskId == null || state.task.id == taskId) {
      return
    }

    viewModelScope.launch {
      taskUseCases.getTask(taskId)?.also { task ->
        state = state.copy(task = task)
      }
    }
  }

  private fun updateTask(transform: (Task) -> Task) {
    viewModelScope.launch {
      val updatedTask = transform(state.task)

      try {
        taskUseCases.addTask(updatedTask)
        state = state.copy(task = updatedTask)
      } catch (_: InvalidTaskException) {
      }
    }
  }

  private fun deleteCurrentTask() {
    viewModelScope.launch {
      taskUseCases.deleteTask(state.task)
    }
  }

  @AssistedFactory
  interface Factory {
    fun create(key: Screen.Task): TaskViewModel
  }
}
