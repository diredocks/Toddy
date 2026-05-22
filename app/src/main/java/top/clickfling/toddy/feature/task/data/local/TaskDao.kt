package top.clickfling.toddy.feature.task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
  @Query("SELECT * FROM taskentity")
  fun getTasks(): Flow<List<TaskEntity>>

  @Query("SELECT * FROM taskentity WHERE id = :id")
  suspend fun getTaskById(id: Int): TaskEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTask(taskEntity: TaskEntity)

  @Delete
  suspend fun deleteTask(taskEntity: TaskEntity)
}