package top.clickfling.toddy.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.clickfling.toddy.feature.todo.data.local.TodoDatebase
import top.clickfling.toddy.feature.todo.data.repository.TodoRepositoryImpl
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository
import top.clickfling.toddy.feature.todo.domain.useCase.AddTodo
import top.clickfling.toddy.feature.todo.domain.useCase.DeleteTodo
import top.clickfling.toddy.feature.todo.domain.useCase.GetTodo
import top.clickfling.toddy.feature.todo.domain.useCase.GetTodos
import top.clickfling.toddy.feature.todo.domain.useCase.TodoUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideTodoDatabase(app: Application): TodoDatebase {
    return Room.databaseBuilder(
      app,
      TodoDatebase::class.java,
      TodoDatebase.DATABASE_NAME
    ).build()
  }

  @Provides
  @Singleton
  fun provideTodoRepository(db: TodoDatebase): TodoRepository {
    return TodoRepositoryImpl(db.todoDao)
  }

  @Provides
  @Singleton
  fun provideTodoUseCases(repository: TodoRepository): TodoUseCases {
    return TodoUseCases(
      getTodo = GetTodo(repository),
      getTodos = GetTodos(repository),
      addTodo = AddTodo(repository),
      deleteTodo = DeleteTodo(repository)
    )
  }
}