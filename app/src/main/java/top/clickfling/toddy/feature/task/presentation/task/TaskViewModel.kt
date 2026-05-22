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
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import top.clickfling.toddy.feature.task.presentation.util.Screen

@HiltViewModel(assistedFactory = TaskViewModel.Factory::class)
class TaskViewModel @AssistedInject constructor(
  private val taskUseCases: TaskUseCases,
  @Assisted private val key: Screen.Task,
) : ViewModel() {
  var state by mutableStateOf(TaskState())
    private set

  private var currentTaskId: Int? = null

  init {
    loadTask(key.taskId)
  }

  private fun loadTask(taskId: Int?) {
    if (taskId == null || currentTaskId == taskId) {
      return
    }

    viewModelScope.launch {
      taskUseCases.getTask(taskId)?.also { task ->
        currentTaskId = task.id
        state = state.copy(
          content = task.content,
          completed = task.completed,
          due = task.due,
          remind = task.remind
        )
      }
    }
  }

  @AssistedFactory
  interface Factory {
    fun create(key: Screen.Task): TaskViewModel
  }
}
