package be.mbolle.crochcounter.patterns.model

data class PatternItem(
    val description: String,
    val comment: String? = null,
    val isTitle: Boolean = false
)