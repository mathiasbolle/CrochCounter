package be.mbolle.crochcounter.patterns.data

import be.mbolle.crochcounter.core.model.PatternRepository

class PatternRoomRepository(private val patternDao: PatternDao) : PatternRepository {
    override suspend fun getPattern(id: Int): be.mbolle.crochcounter.patterns.screens.model.Pattern? {
        return patternDao.getPatternById(id)?.toModel()
    }

    override suspend fun getPatternByName(name: String): be.mbolle.crochcounter.patterns.screens.model.Pattern? {
        return patternDao.getPatternByName(name)?.toModel()
    }

    override suspend fun createPattern(name: String) {
        patternDao.createPattern(
            Pattern(
                name = name
            )
        )
    }

    override suspend fun deletePattern(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPatterns(): List<be.mbolle.crochcounter.patterns.screens.model.Pattern> {
        return patternDao.getAll().toModel()
    }
}

// convert
fun Pattern.toModel(): be.mbolle.crochcounter.patterns.screens.model.Pattern {

    // TODO: add amount of ACTIVE projects per pattern
    return be.mbolle.crochcounter.patterns.screens.model.Pattern(
        name = this.name,
        projectCount = 1
    )
}

fun List<Pattern>.toModel(): List<be.mbolle.crochcounter.patterns.screens.model.Pattern> {
    return this.map { it.toModel() }
}