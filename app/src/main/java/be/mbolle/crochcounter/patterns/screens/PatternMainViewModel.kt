package be.mbolle.crochcounter.patterns.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import be.mbolle.crochcounter.patterns.screens.model.Pattern
import be.mbolle.crochcounter.patterns.screens.model.PatternItem
import be.mbolle.crochcounter.patterns.screens.model.PatternItemSection
import be.mbolle.crochcounter.patterns.screens.model.SubtitlePatternItem

class PatternMainViewModel : ViewModel() {
    lateinit var pattern: MutableState<Pattern>
        private set

    lateinit var patternItemSections: MutableState<List<PatternItemSection>>
        private set

    init {
        loadPattern()
        loadPatternItemSection()
    }

    private fun loadPattern() {
        pattern = mutableStateOf(Pattern(name = "cool pattern(no data)", 2))
    }

    private fun loadPatternItemSection() {
        patternItemSections = mutableStateOf(
            listOf(
                PatternItemSection(
                    title = PatternItem("EARS (make 2)"),
                    subtitles = listOf(
                        SubtitlePatternItem(
                            content = "6 SC in MR (6)"
                        ),
                        SubtitlePatternItem(
                            content = "[SC, INC]x3 (9)"
                        ),
                        SubtitlePatternItem(
                            content = "[2 SC, INC]x3 (12)"
                        ),
                        SubtitlePatternItem(
                            content = "16 SC (16)",
                            range = 4
                        ),
                        SubtitlePatternItem(
                            content = "[2 SC, DEC]x4 (12)"
                        )
                    )
                ),


                PatternItemSection(
                    title = PatternItem("EARS (make 2)"),
                    subtitles = listOf(
                        SubtitlePatternItem(
                            content = "6 SC in MR (6)"
                        ),
                        SubtitlePatternItem(
                            content = "[SC, INC]x3 (9)"
                        ),
                        SubtitlePatternItem(
                            content = "[2 SC, INC]x3 (12)"
                        ),
                        SubtitlePatternItem(
                            content = "16 SC (16)",
                            range = 4
                        ),
                        SubtitlePatternItem(
                            content = "[2 SC, DEC]x4 (12)"
                        )
                    )
                ),

            )
        )
    }
}