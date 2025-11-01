package be.mbolle.crochcounter.projects.presentation.screens.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.core.data.db.CrochDatabase
import be.mbolle.crochcounter.projects.data.ProjectRoomRepository

class SearchProjectViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SearchProjectViewModelFactory? = null

        fun getInstance(context: Context): SearchProjectViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: SearchProjectViewModelFactory(context).also { instance = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // separate instances
        val database = CrochDatabase.getDatabase(context)
        val projectRepository = ProjectRoomRepository(database.crochDao())


        if (modelClass.isAssignableFrom(SearchProjectViewModel::class.java)) {
            return SearchProjectViewModel(
                projectRepository = projectRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}