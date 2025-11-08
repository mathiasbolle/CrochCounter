package be.mbolle.crochcounter.patterns.data

import androidx.room.Embedded
import androidx.room.Relation


//@Entity(primaryKeys = ["patternItemId", "patternId"], tableName = "pattern_pattern_item_details")
data class PatternPatternItemDetail(
    @Embedded val pattern: Pattern,
//    val patternItemId: Long,
//    val patternId: Long
    @Relation(
        parentColumn = "patternId",
        entityColumn = "patternItemId"
    )
    val patternItem: List<PatternItem>
)