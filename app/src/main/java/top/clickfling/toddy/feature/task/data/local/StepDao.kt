package top.clickfling.toddy.feature.task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    @Query("SELECT * FROM step_entity WHERE taskId = :taskId ORDER BY `order` ASC")
    fun getStepsByTaskId(taskId: Int): Flow<List<StepEntity>>

    @Query("SELECT * FROM step_entity WHERE taskId = :taskId ORDER BY `order` ASC")
    suspend fun getStepsByTaskIdOnce(taskId: Int): List<StepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: StepEntity)

    @Update
    suspend fun updateStep(step: StepEntity)

    @Query("DELETE FROM step_entity WHERE id = :stepId")
    suspend fun deleteStepById(stepId: String)

    @Query("DELETE FROM step_entity WHERE taskId = :taskId")
    suspend fun deleteStepsByTaskId(taskId: Int)
}
