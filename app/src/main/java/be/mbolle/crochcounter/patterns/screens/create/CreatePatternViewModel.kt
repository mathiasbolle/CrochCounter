package be.mbolle.crochcounter.patterns.screens.create

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import timber.log.Timber

class CreatePatternViewModel: ViewModel() {
    init {
        Timber.d("called")
    }
    var searchName: TextFieldState = TextFieldState()
        private set

}