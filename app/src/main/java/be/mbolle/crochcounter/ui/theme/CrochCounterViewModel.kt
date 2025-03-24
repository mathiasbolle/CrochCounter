package be.mbolle.crochcounter.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.data.CounterRepository
import kotlinx.coroutines.launch

class CrochCounterViewModel(val counterRepository: CounterRepository): ViewModel() {
    var counter by mutableIntStateOf(0)
        private set

    init {
        viewModelScope.launch {
            counter = counterRepository.getCounter()
        }
    }

    fun addCounterByOne() {
        viewModelScope.launch {
            counterRepository.incrementBy(1)
            counter = counterRepository.getCounter()
        }
    }

    fun subtractCounterByOne() {
        viewModelScope.launch {
            if (counter > 0) {
                counterRepository.decreaseBy(1)
                counter = counterRepository.getCounter()
            }
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            counterRepository.reset()
            counter = counterRepository.getCounter()
        }
    }
}