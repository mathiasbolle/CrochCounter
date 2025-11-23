package be.mbolle.crochcounter.patterns.screens.create

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.patterns.model.PatternItem
import be.mbolle.crochcounter.patterns.model.use_cases.CreatePatternUseCase
import be.mbolle.crochcounter.patterns.screens.create.patternItems.model.PatternItemPart
import kotlinx.coroutines.launch
import timber.log.Timber


class CreatePatternViewModel(
    private val createPatternUseCase: CreatePatternUseCase,
) : ViewModel() {

    var patternName: TextFieldState = TextFieldState()
        private set

    private var trackedIndex = 0

    private val subpatternsList = mutableStateListOf<PatternItemPart?>(PatternItemPart())

    val subpatterns = subpatternsList[trackedIndex]

    fun addSubPatternContent(index: Int, content: String) {
        //subpatterns?.patternItemList?.get(index)?.copy(content = content)
        Timber.d("You are getting called!")
    }

    fun addEmptySubPattern() {
        // generate a new subpattern
        subpatterns?.patternItemList?.add(null)
    }

    fun addEmptyPatternItem() {
        subpatternsList.add(
            PatternItemPart()
        )
        trackedIndex++
    }

    fun confirm() {
        for (subpatternItem in subpatternsList) {
            Timber.d(subpatternItem.toString())
            Timber.d(subpatternItem?.patternItemList.toString())
            viewModelScope.launch {

                subpatternItem?.patternItemList?.map {
                    PatternItem(
                        description = it?.content?.text.toString(),
                        isTitle = false
                    )
                }?.plus(
                    PatternItem(
                        description = subpatternItem.subTitle.text.toString(),
                        isTitle = true
                    )
                )?.let {
                    createPatternUseCase(
                        patternName.text.toString(),
                        subPatterns =
                            it
                    )
                }
            }
        }
    }
}