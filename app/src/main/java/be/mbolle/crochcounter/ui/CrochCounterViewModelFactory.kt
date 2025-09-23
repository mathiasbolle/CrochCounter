package be.mbolle.crochcounter.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.projects.data.ProjectRoomRepository
import be.mbolle.crochcounter.core.data.db.CrochDatabase
import be.mbolle.crochcounter.projects.model.use_cases.GetActiveProjectUseCase
import timber.log.Timber

class CrochCounterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

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
        val projectRepository = ProjectRoomRepository(database.crochDao())

        val getActiveProjectUseCase = GetActiveProjectUseCase(projectRepository)

        Timber.log(Log.WARN, database.toString())
        Timber.log(Log.WARN, "test")

        if (modelClass.isAssignableFrom(CrochCounterViewModel::class.java)) {

            return CrochCounterViewModel(
                projectRepository = projectRepository,
                getActiveProjectUseCase = getActiveProjectUseCase
            ) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}