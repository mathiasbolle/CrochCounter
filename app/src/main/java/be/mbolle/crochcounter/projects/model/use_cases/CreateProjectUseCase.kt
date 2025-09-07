package be.mbolle.crochcounter.projects.model.use_cases

import be.mbolle.crochcounter.core.data.db.PatternProjectDetails
import be.mbolle.crochcounter.core.data.db.ProjectWithPatternDao
import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.projects.data.Project

/**
 * Prerequisites:
 * 1. Pattern should be already existing.
 * 2. Many to many table should be created (with active line)
 * 3. Name of the project based on the pattern
 */
class CreateProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val patternRepository: PatternRepository,
    private val projectWithPatternDao: ProjectWithPatternDao
) {

    suspend operator fun invoke(patternId: Int): Result<Unit> {
        return try {
            val getBasePattern = validateExistingPattern(patternId)
            val getCreatedProject = singleProjectCreation(getBasePattern)

            createPatternProject(
                pattern = getBasePattern,
                project = getCreatedProject
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun validateExistingPattern(patternId: Int): Pattern {
        val basePattern = patternRepository.getPattern(patternId)

        if (basePattern == null) {
            throw IllegalArgumentException(
                Exception("There is no valid pattern present.")
            )
        }

        return basePattern
    }

    private suspend fun singleProjectCreation(
        pattern: Pattern
    ): Project {
        val pattern = pattern
        // get all projects with this pattern and check if it already exist
        // then generate the name of the pattern in the following form
        // PATTERNAME NUMBER
        val alreadyExistingProjects =
            projectWithPatternDao
                .getProjectsWithPatternId(pattern.patternId)
                .count()

        val patternName = "${pattern.name} ${alreadyExistingProjects + 1}"
        val project = projectRepository.createProject(patternName)

        return project!!
    }

    private suspend fun createPatternProject(
        pattern: Pattern,
        project: Project
    ): Result<PatternProjectDetails> {
        return runCatching {
            val pattern = pattern
            val project = project

            projectWithPatternDao.createProjectWithPattern(
                PatternProjectDetails(
                    project.projectId,
                    pattern.patternId,
                )
            )

            return@runCatching projectWithPatternDao.getPatternProjectDetails(
                patternId = pattern.patternId,
                projectId = project.projectId
            )
        }
    }
}