package be.mbolle.crochcounter.patterns.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.core.presentation.navigation.PatternScreens
import be.mbolle.crochcounter.patterns.data.PatternItemDao
import be.mbolle.crochcounter.patterns.data.PatternWithPatternItemDao
import be.mbolle.crochcounter.patterns.screens.model.Pattern
import be.mbolle.crochcounter.patterns.screens.model.PatternItem
import be.mbolle.crochcounter.patterns.screens.model.PatternItemSection
import be.mbolle.crochcounter.patterns.screens.model.SubtitlePatternItem
import kotlinx.coroutines.launch

class PatternMainViewModel(
    private val patternRepository: PatternRepository,
    private val patternWithPatternItemDao: PatternWithPatternItemDao,
    private val patternItemDao: PatternItemDao,
    savedStateHandle: SavedStateHandle,
) : ViewModel(

) {

    private var selectedPatternId: Int = -1

    private val patternParams = savedStateHandle.toRoute<PatternScreens.PatternMainScreen>()

    var pattern: MutableState<Pattern?> = mutableStateOf(null)
        private set

    var patternItemSections: MutableState<List<PatternItemSection>> = mutableStateOf(emptyList())
        private set

    init {
        loadPattern()
        loadPatternItemSection()
    }

    private fun loadPattern() {
        viewModelScope.launch {
            patternRepository.getPatternByName(patternParams.name)
                ?.let {
                    pattern.value = (Pattern(it.name, 1))
                }
        }
    }


    private fun loadPatternItemSection() {
        /*
        val patternitemsubtitles =
            patternitemdao.getpatternitemsubtitle(patternitemtitle.patternitemid)

         */
        viewModelScope.launch {
            patternItemSections.value =
                patternWithPatternItemDao.getPatternItemsFromPattern(1)
                    .single().patternItem
                    .filter {
                        it.patternItemRef == null
                    }
                    .map {
                        val subtitles = patternItemDao
                            .getPatternItemSubtitle(it.patternItemId)
                            .map { subtitle -> SubtitlePatternItem(content = subtitle.content) }

                        return@map PatternItemSection(
                            title = PatternItem(content = it.content),
                            subtitles = subtitles
                        )
                    }
        }
//        patternItemSections = mutableStateOf(
//            listOf(
//                PatternItemSection(
//                    title = PatternItem("EARS (make 2)"),
//                    subtitles = listOf(
//                        SubtitlePatternItem(
//                            content = "6 SC in MR (6)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "[SC, INC]x3 (9)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "[2 SC, INC]x3 (12)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "16 SC (16)",
//                            range = 4
//                        ),
//                        SubtitlePatternItem(
//                            content = "[2 SC, DEC]x4 (12)"
//                        )
//                    )
//                ),
//
//
//                PatternItemSection(
//                    title = PatternItem("EARS (make 2)"),
//                    subtitles = listOf(
//                        SubtitlePatternItem(
//                            content = "6 SC in MR (6)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "[SC, INC]x3 (9)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "[2 SC, INC]x3 (12)"
//                        ),
//                        SubtitlePatternItem(
//                            content = "16 SC (16)",
//                            range = 4
//                        ),
//                        SubtitlePatternItem(
//                            content = "[2 SC, DEC]x4 (12)"
//                        )
//                    )
//                ),
//
//                )
//        )
    }
}