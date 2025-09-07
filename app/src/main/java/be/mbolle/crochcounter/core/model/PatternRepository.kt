package be.mbolle.crochcounter.core.model

import be.mbolle.crochcounter.patterns.data.Pattern

interface PatternRepository {
    suspend fun getPattern(id: Int): Pattern?
    suspend fun createPattern(name: String): Pattern?
    suspend fun deletePattern(name: String)
}