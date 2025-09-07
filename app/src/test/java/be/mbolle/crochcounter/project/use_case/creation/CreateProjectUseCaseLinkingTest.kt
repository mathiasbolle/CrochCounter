package be.mbolle.crochcounter.project.use_case.creation

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Linking of a Project
 */
class CreateProjectUseCaseLinkingTest : CreateProjectUseCaseCore() {

    @Test
    override fun testExecution() = runTest {
        patternRepository.createPattern("Cow") // implicit

        useCase(
            patternId = 1
        )

        assertEquals(
            1,
            projectWithPatternDao.getProjectsWithPatternId(1).size
        )
    }
}