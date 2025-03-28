package be.mbolle.crochcounter.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.data.CounterDatastoreRepository
import be.mbolle.crochcounter.data.CounterRepository

class CrochCounterViewModelFactory private constructor(private val context: Context): ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: CrochCounterViewModelFactory? = null

        fun getInstance(context: Context): CrochCounterViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: CrochCounterViewModelFactory(context).also { instance = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CounterDatastoreRepository.getInstance(context)

        return modelClass.getConstructor(
            CounterRepository::class.java
        ).newInstance(repository)
    }
}