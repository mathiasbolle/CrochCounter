package be.mbolle.crochcounter.project

import be.mbolle.crochcounter.core.data.db.PatternProjectDetails
import be.mbolle.crochcounter.core.data.db.ProjectWithPatternDao
import be.mbolle.crochcounter.core.data.db.ProjectWithPatterns
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.projects.data.Project

class FakeProjectWithPatternDao : ProjectWithPatternDao {
    private val relationships = mutableListOf<PatternProjectDetails>()

    override suspend fun getProjectsWithPatternId(patternId: Int): List<ProjectWithPatterns> {
        return relationships
            .filter { it.patternId == patternId }
            .map {
                ProjectWithPatterns(
                    pattern = Pattern(name = ""),
                    projects = emptyList()
                )
            }
    }

    override suspend fun createProjectWithPattern(details: PatternProjectDetails): Long {
        relationships.add(details)
        return 0L
    }

    override suspend fun getPatternProjectDetails(
        patternId: Int,
        projectId: Int
    ): PatternProjectDetails {
        TODO("Not yet implemented")
    }
}