package top.clickfling.toddy.feature.task.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "step_entity",
    foreignKeys = [
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
        )
    ],
    indices = [Index("taskId")]
)
data class StepEntity(
    @PrimaryKey val id: String,
    val content: String,
    val completed: Boolean = false,
    val taskId: Int,
    val order: Int = 0,
)
