package top.clickfling.toddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import top.clickfling.toddy.feature.todo.presentation.todos.TodosScreenRoute
import top.clickfling.toddy.feature.todo.presentation.util.Screen
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
          NavHost (
            navController = navController,
            startDestination = Screen.TodosScreen.route
          ) {
            composable(route = Screen.TodosScreen.route) {
              TodosScreenRoute(navController = navController)
            }
          }
        }
      }
    }
  }
}