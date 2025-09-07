package be.mbolle.crochcounter.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ProjectWithPatternDao {
    @Transaction
    @Query("SELECT * FROM patterns where patternId = :patternId")
    suspend fun getProjectsWithPatternId(patternId: Int): List<ProjectWithPatterns> // the view

    @Insert
    suspend fun createProjectWithPattern(projectWithPatterns: PatternProjectDetails): Long

    @Query("SELECT * FROM pattern_project_details where patternId = :patternId AND projectId = :projectId")
    suspend fun getPatternProjectDetails(patternId: Int, projectId: Int): PatternProjectDetails
}