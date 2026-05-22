package top.clickfling.toddy.feature.task.presentation.util

sealed class Screen(val route: String) {
  object TasksScreen : Screen("tasks")
}