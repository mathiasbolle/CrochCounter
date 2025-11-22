package be.mbolle.crochcounter.patterns.screens.create.patternItems.model

data class PatternItem(
    val content: String ,
    val comment: String?=null,
    val rangeFrom: Int = 1,
    val rangeTo: Int? = null
)