package be.mbolle.crochcounter.project.use_case.creation

import be.mbolle.crochcounter.projects.data.Project
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CreateProjectUseCasePatternIdsTest(
    val patternId: Int, val actualFailureResult: Boolean
) : CreateProjectUseCaseCore() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
            name = "Given Pattern with ID {0}, should result failure result {1}"
        )

        fun getValidateExistingPattern(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(
                    -1,
                    true
                ),
                arrayOf(
                    Int.MAX_VALUE,
                    true
                ),
                arrayOf(
                    1,
                    false
                ),
                arrayOf(
                    2,
                    false
                )
            )
        }
    }

    @Test
    override fun testExecution(
    ) = runTest {
        //Given
        patternRepository.createPattern("Cow") // implicit
        patternRepository.createPattern("Bee 🐝") // implicit

        val result: Result<be.mbolle.crochcounter.projects.model.Project> = useCase(
            patternId = patternId
        )

        assertEquals(
            result.isFailure,
            actualFailureResult
        )
    }
}