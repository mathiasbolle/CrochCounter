package be.mbolle.crochcounter.patterns.screens.create.patternItems.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class PatternItemPart(
    val subTitle: TextFieldState = TextFieldState(),
    val patternItemList: SnapshotStateList<PatternItem?> = mutableStateListOf(PatternItem())
)

data class PatternItem(
    val content: TextFieldState = TextFieldState(),
    val comment: String? = null,
    val rangeFrom: Int = 1,
    val rangeTo: Int? = null
)