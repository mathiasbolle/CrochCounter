package be.mbolle.crochcounter.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.data.CounterDatastoreRepository
import be.mbolle.crochcounter.data.CounterRepository

class CrochCounterViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CounterDatastoreRepository(context)

        return modelClass.getConstructor(
            CounterRepository::class.java
        ).newInstance(repository)
    }
}