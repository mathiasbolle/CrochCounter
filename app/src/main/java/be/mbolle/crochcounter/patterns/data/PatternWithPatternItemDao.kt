package be.mbolle.crochcounter.patterns.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PatternWithPatternItemDao {
    @Transaction
    @Query("SELECT * FROM patterns WHERE patternId = :patternId")
    suspend fun getPatternItemsFromPattern(patternId: Int): List<PatternPatternItemDetail>
}