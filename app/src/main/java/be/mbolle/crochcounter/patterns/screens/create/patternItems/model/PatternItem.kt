package be.mbolle.crochcounter.patterns.screens.create.patternItems.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class PatternItemPart(
    val subTitle: String? = null,
    val patternItemList: SnapshotStateList<PatternItem?> = mutableStateListOf(null)
)

data class PatternItem(
    val content: String,
    val comment: String? = null,
    val rangeFrom: Int = 1,
    val rangeTo: Int? = null
)