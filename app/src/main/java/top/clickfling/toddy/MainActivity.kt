package top.clickfling.toddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import top.clickfling.toddy.feature.task.presentation.task.TaskScreenRoute
import top.clickfling.toddy.feature.task.presentation.task.TaskViewModel
import top.clickfling.toddy.feature.task.presentation.tasks.TasksScreenRoute
import top.clickfling.toddy.feature.task.presentation.util.Screen
import top.clickfling.toddy.ui.theme.ToddyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ToddyTheme {
        Surface(
          color = MaterialTheme.colorScheme.background
        ) {
          val backStack = rememberNavBackStack(Screen.Tasks)

          NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
              rememberSaveableStateHolderNavEntryDecorator(),
              rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
              entry<Screen.Tasks> {
                TasksScreenRoute(
                  onTaskClick = { taskId ->
                    backStack.add(Screen.Task(taskId))
                  }
                )
              }
              entry<Screen.Task> { key ->
                val viewModel = hiltViewModel<TaskViewModel, TaskViewModel.Factory> { factory ->
                  factory.create(key)
                }
                TaskScreenRoute(
                  viewModel = viewModel,
                  onBackClick = { backStack.removeLastOrNull() }
                )
              }
            }
          )
        }
      }
    }
  }
}
