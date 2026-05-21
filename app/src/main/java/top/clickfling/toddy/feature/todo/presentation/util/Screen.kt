package top.clickfling.toddy.feature.todo.presentation.util

sealed class Screen(val route: String) {
  object TodosScreen : Screen("todos")
}