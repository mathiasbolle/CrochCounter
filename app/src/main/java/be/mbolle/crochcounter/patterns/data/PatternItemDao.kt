package be.mbolle.crochcounter.patterns.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PatternItemDao {
    @Query("SELECT * FROM pattern_items WHERE patternItemId = :id AND patternItemRef IS NULL")
    suspend fun getPatternItemTitle(id: Long): PatternItem

    @Query("SELECT * FROM pattern_items WHERE patternItemRef = :parentId")
    suspend fun getPatternItemSubtitle(parentId: Long): List<PatternItem>
}