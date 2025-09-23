package be.mbolle.crochcounter.project.use_case.creation

import kotlinx.coroutines.test.runTest
import org.junit.Test
import junit.framework.TestCase.assertEquals

/**
 * base case for Project name creation.
 */
class CreateProjectUseCaseProjectNameTest : CreateProjectUseCaseCore() {

    @Test
    override fun testExecution() = runTest {
        patternRepository.createPattern("Cow") // implicit

        useCase(
            patternId = 1
        )

        val expectedProjectName = "Cow 1"
        assertEquals(
            expectedProjectName,
            projectRepository.getAllProjects()?.single()?.name
        )
    }
}