package be.mbolle.crochcounter.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CrochCounterViewModel(): ViewModel() {
    var counter by mutableIntStateOf(0)
        private set

    fun addCounterByOne() {
        counter++
    }

    fun subtractCounterByOne() {
        counter--
    }

    fun resetCounter() {
        counter = 0
    }
}