package be.mbolle.crochcounter.patterns.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pattern_items")
data class PatternItem(
    @PrimaryKey(autoGenerate = true) val patternItemId: Long = 0,
    val content: String,
//    @ColumnInfo(name = "item_kind")
//    val kind: PatternItemKind,
    val comment: String? = null,
    val patternId: Int,
    val patternItemRef: Long? = null, //to know to which title contains the subtitles
)

/**
 * This is currently not needed,
 * this information can be inferred.
 *

enum class PatternItemKind {
    TITLE, SUBTITLE
}

class PatternItemKindConverter {
    @TypeConverter
    fun toPatternItemKind(value: String) = enumValueOf<PatternItemKind>(value)

    @TypeConverter
    fun fromPatternItemKind(value: PatternItemKind) = value.name
}
 */
