package be.mbolle.crochcounter.core.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.projects.data.Project

data class ProjectWithPatterns(
    @Embedded val pattern: Pattern,
    @Relation(
        parentColumn = "patternId",
        entityColumn = "projectId",
        associateBy = Junction(
            PatternProjectDetails::class
        )
    )
    val projects: List<Project>
)