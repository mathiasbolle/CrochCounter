package be.mbolle.crochcounter.projects.model

import be.mbolle.crochcounter.patterns.model.PatternItem

class Pattern(
    val activeLine: Int,
    title: String,
    private val subPatterns: List<SubPattern>
) {
    init {
        assert(activeLine > 0 || activeLine < patterns().size) {
            "Pattern $title should have a valid range between 1 and ${patterns().size}."
        }
        assert(title != "") { "Title of the pattern should not be empty." }
        assert(subPatterns.isNotEmpty()) { "Pattern should have at least 1 subPattern." }
    }

    /**
     * 5 patterns
     */
    fun patterns(amount: Int = 5): List<PatternItem> {
        return subPatterns
            .filter { it.subtitle == getSubtitle()}
            .map { pattern -> pattern.lines }
            .flatten()
            .take(amount)
    }

    fun getSubtitle(): String {
        val subtitlePatternItemPair = subPatterns
            .map { pattern -> Pair(pattern.subtitle, pattern.lines) }[activeLine - 1]
        return subtitlePatternItemPair.first
    }
}
