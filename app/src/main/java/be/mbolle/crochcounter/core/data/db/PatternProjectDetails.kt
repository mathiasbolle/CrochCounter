package be.mbolle.crochcounter.core.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["projectId", "patternId"], tableName = "pattern_project_details")
data class PatternProjectDetails (
    val projectId: Int,
    val patternId: Int,
    val activeLine: Int = 1,
)