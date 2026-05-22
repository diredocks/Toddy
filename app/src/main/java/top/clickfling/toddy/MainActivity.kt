package top.clickfling.toddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import top.clickfling.toddy.feature.task.presentation.task.TaskScreenRoute
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
          val navController = rememberNavController()
          NavHost(
            navController = navController,
            startDestination = Screen.TasksScreen.route,
            enterTransition = {
              slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
              )
            },
            exitTransition = {
              slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
              )
            },
            popEnterTransition = {
              slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
              )
            },
            popExitTransition = {
              slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
              )
            }
          ) {
            composable(route = Screen.TasksScreen.route) {
              TasksScreenRoute(navController = navController)
            }
            composable(
              route = Screen.TaskScreen.route + "?taskId={taskId}",
              arguments = listOf(
                navArgument(name = "taskId") {
                  type = NavType.IntType
                  defaultValue = -1
                }
              )
            ) {
              TaskScreenRoute(navController = navController)
            }
          }
        }
      }
    }
  }
}