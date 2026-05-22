package top.clickfling.toddy.feature.task.presentation.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {
  var state by mutableStateOf(TaskState())
    private set

  private var currentTaskId: Int? = null
  init {
    savedStateHandle.get<Int>("taskId")?.let { taskId ->
      if (taskId == -1) return@let

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
  }
}