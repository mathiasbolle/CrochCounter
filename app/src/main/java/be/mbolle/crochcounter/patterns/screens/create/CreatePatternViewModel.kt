package be.mbolle.crochcounter.patterns.screens.create

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import be.mbolle.crochcounter.patterns.model.use_cases.CreatePatternUseCase
import be.mbolle.crochcounter.patterns.screens.create.patternItems.model.PatternItem


class CreatePatternViewModel(
    private val createPatternUseCase: CreatePatternUseCase,

    ) : ViewModel() {

    var searchName: TextFieldState = TextFieldState()
        private set

    var subpatterns =
        mutableStateListOf<PatternItem?>(null)
        private set


    fun adSubPatternContent(content: String) {
    }

    fun addNewSubPattern() {
        // generate a new subpattern
        subpatterns.add(null) //implicitly add a new subpattern
    }

    fun confirm() {

    }
}