package top.clickfling.toddy.feature.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
  @Query("SELECT * FROM todoentity ORDER BY completed")
  fun getTodos(): Flow<List<TodoEntity>>

  @Query("SELECT * FROM todoentity WHERE id = :id")
  suspend fun getTodoById(id: Int): TodoEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTodo(todoEntity: TodoEntity)

  @Delete
  suspend fun deleteTodo(todoEntity: TodoEntity)
}