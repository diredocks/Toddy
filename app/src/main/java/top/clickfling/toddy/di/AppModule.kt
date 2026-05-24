package top.clickfling.toddy.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.clickfling.toddy.feature.task.data.local.TaskDatabase
import top.clickfling.toddy.feature.task.data.repository.TaskRepositoryImpl
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository
import top.clickfling.toddy.feature.task.domain.useCase.AddTask
import top.clickfling.toddy.feature.task.domain.useCase.DeleteTask
import top.clickfling.toddy.feature.task.domain.useCase.GetTask
import top.clickfling.toddy.feature.task.domain.useCase.GetTasks
import top.clickfling.toddy.feature.task.domain.useCase.TaskUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Provides
  @Singleton
  fun provideTaskDatabase(app: Application): TaskDatabase {
    return Room.databaseBuilder(
      app,
      TaskDatabase::class.java,
      TaskDatabase.DATABASE_NAME
    ).build()
  }

  @Provides
  @Singleton
  fun provideTaskRepository(db: TaskDatabase): TaskRepository {
    return TaskRepositoryImpl(db.taskDao)
  }

  @Provides
  @Singleton
  fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
    return TaskUseCases(
      getTask = GetTask(repository),
      getTasks = GetTasks(repository),
      addTask = AddTask(repository),
      deleteTask = DeleteTask(repository),
    )
  }
}