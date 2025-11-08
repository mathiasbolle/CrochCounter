package be.mbolle.crochcounter.core.model

import be.mbolle.crochcounter.patterns.screens.model.Pattern


interface PatternRepository{
    suspend fun getPattern(id: Int): Pattern?

    suspend fun getPatternByName(name: String): be.mbolle.crochcounter.patterns.data.Pattern?
    suspend fun createPattern(name: String)
    suspend fun deletePattern(name: String)

    suspend fun getAllPatterns(): List<Pattern>
}