package top.clickfling.toddy.feature.task.presentation.tasks

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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import top.clickfling.toddy.feature.task.domain.model.InvalidTaskException
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import top.clickfling.toddy.feature.task.domain.util.TaskOrder
import top.clickfling.toddy.feature.task.presentation.common.util.toDueDays
import top.clickfling.toddy.feature.task.presentation.common.util.toRemindTime
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class TasksViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases
) : ViewModel() {
  var state by mutableStateOf(TasksState())
    private set

  private var getTasksJob: Job? = null

  init {
    getTasks(state.taskOrder)
  }

  fun onEvent(event: TasksEvents) {
    when (event) {
      is TasksEvents.DeleteTask -> {
        viewModelScope.launch {
          taskUseCases.deleteTask(event.task)
          state = state.copy(recentlyDeletedTask = event.task)
        }
      }

      is TasksEvents.ToggleCompleted -> {
        viewModelScope.launch {
          try {
            taskUseCases.addTask(
              event.task.copy(
                id = event.task.id,
                completed = !event.task.completed
              )
            )
          } catch (e: InvalidTaskException) {

          }
        }
      }

      is TasksEvents.ToggleImportance -> {
        viewModelScope.launch {
          try {
            taskUseCases.addTask(
              event.task.copy(
                id = event.task.id,
                important = !event.task.important
              )
            )
          } catch (e: InvalidTaskException) {

          }
        }
      }

      is TasksEvents.Order -> {
        if (state.taskOrder == event.taskOrder) return
        state = state.copy(taskOrder = event.taskOrder)
      }

      is TasksEvents.EnteredContent -> {
        state = state.copy(content = event.content)
      }

      is TasksEvents.SelectDue -> {
        state = state.copy(due = event.dueSelection.toDueDays())
      }

      is TasksEvents.SelectRemind -> {
        state = state.copy(remind = event.remindSelection.toRemindTime())
      }

      is TasksEvents.StoreRecentlyDeletedTask -> {
        state = state.copy(recentlyDeletedTask = event.task)
      }

      TasksEvents.DeleteRecentlyDeletedTask -> {
        state = state.copy(recentlyDeletedTask = null)
      }

      TasksEvents.RestoreTask -> {
        viewModelScope.launch {
          taskUseCases.addTask(state.recentlyDeletedTask ?: return@launch)
          state = state.copy(recentlyDeletedTask = null)
        }
      }

      TasksEvents.ToggleCompletedVisibility -> {
        state = state.copy(showCompleted = !state.showCompleted)
      }

      TasksEvents.ToggleSheetVisibility -> {
        state = state.copy(
          showSheet = !state.showSheet,
          due = Clock.System.todayIn(TimeZone.currentSystemDefault()),
          remind = null,
          content = "",
        )
      }

      TasksEvents.SaveTask -> {
        viewModelScope.launch {
          try {
            taskUseCases.addTask(
              Task(
                content = state.content,
                due = state.due,
                remind = state.remind,
                completed = false,
                creation = Clock.System.now(),
              )
            )
            state = state.copy(showSheet = !state.showSheet)
          } catch (e: InvalidTaskException) {
            // TODO: Show error via snack bar
          }
        }
      }
    }
  }

  private fun getTasks(order: TaskOrder) {
    getTasksJob?.cancel()
    getTasksJob = taskUseCases.getTasks(order)
      .onEach {
        state = state.copy(
          tasks = it,
          taskOrder = order
        )
      }
      .launchIn(viewModelScope)
  }
}
