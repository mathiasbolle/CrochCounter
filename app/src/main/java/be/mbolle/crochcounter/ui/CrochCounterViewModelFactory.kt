package be.mbolle.crochcounter.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.data.CounterRepository
import be.mbolle.crochcounter.data.CounterRoomRepository
import be.mbolle.crochcounter.data.CrochDatabase

class CrochCounterViewModelFactory private constructor(private val context: Context): ViewModelProvider.Factory {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: CrochCounterViewModelFactory? = null

        fun getInstance(context: Context): CrochCounterViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: CrochCounterViewModelFactory(context).also { instance = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = CrochDatabase.getDatabase(context)
        val repository = CounterRoomRepository(database.crochDao())

        return modelClass.getConstructor(
            CounterRepository::class.java
        ).newInstance(repository)
    }
}