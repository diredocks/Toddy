package top.clickfling.toddy.feature.task.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.clickfling.toddy.feature.task.domain.model.Step

class StepListConverter {
  private val json = Json { ignoreUnknownKeys = true }

  @TypeConverter
  fun fromStepList(value: List<Step>): String =
    json.encodeToString(value)

  @TypeConverter
  fun toStepList(value: String): List<Step> =
    json.decodeFromString(value)
}