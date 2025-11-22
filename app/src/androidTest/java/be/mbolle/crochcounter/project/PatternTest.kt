package be.mbolle.crochcounter.project

import androidx.compose.ui.test.junit4.createComposeRule
import be.mbolle.crochcounter.projects.model.Pattern
import be.mbolle.crochcounter.patterns.model.PatternItem
import be.mbolle.crochcounter.projects.model.SubPattern
import be.mbolle.crochcounter.projects.presentation.composables.PatternTimeline
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PatternTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun before() {
        val previewPattern = Pattern(
            1,
            title = "Bee",
            subPatterns = listOf(
                SubPattern(
                    "EARS",
                    lines = listOf(
                        PatternItem(
                            description = "R1: 6 SC in a MR (6)",
                            comment = "cool"
                        ),
                        PatternItem(
                            description = "R2: [SC, INC]x3 (9)"
                        ),
                        PatternItem(
                            description = "R3: [2 SC, INC]x3 (12)"
                        ),
                        PatternItem(
                            description = "R5- 8:(4 Rounds) 16 SC (16)"
                        )
                    )
                )
            )
        )

        rule.setContent {
            PatternTimeline(
                patterns = previewPattern
            )
        }
    }

    @Test
    fun x() {



    }
}