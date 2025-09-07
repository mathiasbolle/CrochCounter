package be.mbolle.crochcounter.patterns.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patterns")
class Pattern (
    @PrimaryKey(autoGenerate = true) val patternId: Int = 0,
    @ColumnInfo("name") val name: String
)