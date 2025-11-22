package be.mbolle.crochcounter.patterns.model.use_cases

import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.patterns.data.PatternItem
import be.mbolle.crochcounter.patterns.data.PatternItemDao

/**
 * 1. Create a pattern
 * 2. Create all the pattern items
 *  -> patternItemRef column = null:
 *    title
 *  -> patternItemRef column != null: <- VALID reference
 *    subtitle - actual content
 */
class CreatePatternUseCase(
    private val patternRepository: PatternRepository,
    private val patternItemDao: PatternItemDao
) {

    suspend operator fun invoke(
        name: String,
        subPatterns: List<be.mbolle.crochcounter.patterns.model.PatternItem>
    ) {
        val newPattern = createPattern(name)

        createPatternItems(subPatterns, patternId = newPattern.patternId)
    }

    private suspend fun createPattern(name: String): Pattern {
        if (!name.isEmpty()) {
            throw IllegalArgumentException("no.")

        }
        return patternRepository.createPattern(name)
    }

    private suspend fun createPatternItems(
        subPatterns: List<be.mbolle.crochcounter.patterns.model.PatternItem>,
        patternId: Int
    ) {
        //create the title as subpattern
        val title = subPatterns.single { it.isTitle }
        val id = patternItemDao.insertPatternItem(
            PatternItem(
                content = title.description,
                comment = title.comment,
                patternId = patternId,
            )
        )
        val patternItemTitle = patternItemDao.getPatternItemTitle(id)

        //refer all the subpatterns to that title
        subPatterns
            .forEach {
                patternItemDao.insertPatternItem(
                    PatternItem(
                        content = it.description,
                        comment = it.comment,
                        patternId = patternId,
                        patternItemRef = patternItemTitle.patternItemId
                    )
                )
            }
    }
}