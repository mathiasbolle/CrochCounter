package be.mbolle.crochcounter.project.use_case.creation

import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.project.utils.FakePatternRepository
import be.mbolle.crochcounter.project.utils.FakeProjectRepository
import be.mbolle.crochcounter.project.utils.FakeProjectWithPatternDao
import be.mbolle.crochcounter.projects.model.use_cases.CreateProjectUseCase
import org.junit.Before
import org.junit.Test

abstract class CreateProjectUseCaseCore {
    lateinit var useCase: CreateProjectUseCase
    lateinit var projectRepository: ProjectRepository
    lateinit var patternRepository: PatternRepository
    lateinit var projectWithPatternDao: FakeProjectWithPatternDao

    @Before
    fun setup() {
        projectRepository = FakeProjectRepository()
        patternRepository = FakePatternRepository()
        projectWithPatternDao = FakeProjectWithPatternDao(
        )
        useCase = CreateProjectUseCase(
            projectRepository = projectRepository,
            patternRepository = patternRepository,
            projectWithPatternDao = projectWithPatternDao
        )
    }

    @Test
    abstract fun testExecution()
}