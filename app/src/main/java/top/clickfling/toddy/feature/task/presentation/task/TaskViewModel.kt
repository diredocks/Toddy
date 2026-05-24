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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import top.clickfling.toddy.feature.task.domain.model.InvalidTaskException
import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import top.clickfling.toddy.feature.task.presentation.common.util.toDueDays
import top.clickfling.toddy.feature.task.presentation.common.util.toRemindTime
import top.clickfling.toddy.feature.task.presentation.util.Screen

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel(assistedFactory = TaskViewModel.Factory::class)
class TaskViewModel @AssistedInject constructor(
  private val taskUseCases: TaskUseCases,
  @Assisted private val key: Screen.Task,
) : ViewModel() {
  var state by mutableStateOf(TaskState())
    private set

  private var stepsJob: Job? = null

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

      is TaskEvent.AddStep -> {
        val taskId = state.task.id ?: return
        val newStep = Step(
          id = Uuid.random().toString(),
          content = event.content,
          order = state.steps.size,
        )
        viewModelScope.launch {
          taskUseCases.addStep(newStep, taskId)
        }
      }

      is TaskEvent.UpdateStepContent -> {
        val taskId = state.task.id ?: return
        if (event.content.isBlank()) {
          viewModelScope.launch {
            taskUseCases.deleteStep(event.stepId)
          }
        } else {
          val currentStep = state.steps.find { it.id == event.stepId } ?: return
          viewModelScope.launch {
            taskUseCases.updateStep(currentStep.copy(content = event.content), taskId)
          }
        }
      }

      is TaskEvent.ToggleStepCompleted -> {
        val taskId = state.task.id ?: return
        val currentStep = state.steps.find { it.id == event.stepId } ?: return
        viewModelScope.launch {
          taskUseCases.updateStep(currentStep.copy(completed = !currentStep.completed), taskId)
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

    stepsJob?.cancel()
    stepsJob = taskUseCases.getStepsForTask(taskId)
      .onEach { steps ->
        state = state.copy(steps = steps)
      }
      .launchIn(viewModelScope)
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
