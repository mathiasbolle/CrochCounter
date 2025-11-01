package be.mbolle.crochcounter.patterns.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PatternDao {
    @Insert
    suspend fun createPattern(pattern: Pattern): Long

    @Query("SELECT * FROM patterns WHERE patternId = :id")
    suspend fun getPatternById(id: Int): Pattern?


    @Query("SELECT * FROM patterns")
    suspend fun getAll(): List<Pattern>


    @Query("SELECT * FROM patterns WHERE name = :name")
    suspend fun getPatternByName(name: String): Pattern?
}