package be.mbolle.crochcounter.project.utils

import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.patterns.data.Pattern

class FakePatternRepository(
    val datastore: MutableList<Pattern> = mutableListOf()
) : PatternRepository {
    override suspend fun getPattern(id: Int): Pattern? {
        val result = datastore.find {
            it.patternId == id
        }
        return result
    }

    internal fun findById(id: Int): Pattern {
        val patternByName = datastore
            .find { it.patternId == id }

        return patternByName!!
    }

    override suspend fun createPattern(name: String): Pattern? {
        datastore.add(
            Pattern(
                name = name,
                patternId = datastore.size + 1
            )
        )
        return datastore.last()
    }

    override suspend fun deletePattern(name: String) {
        val pattern = datastore.find { it.name == name }
        datastore.remove(pattern)
    }
}