package be.mbolle.crochcounter.patterns.screens.model

data class PatternItemSection(
    val title: PatternItem,
    val subtitles: List<SubtitlePatternItem>
)

open class PatternItem(
    open val content: String,
    open val comment: String? = null
)

/**
 * @param range = how many it applies to, default to 1.
 */
class SubtitlePatternItem(
    override val content: String,
    override val comment: String? = null,
    val range: Int = 1
): PatternItem(content, comment)