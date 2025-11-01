package be.mbolle.crochcounter.core.presentation.screens.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.core.data.db.CrochDatabase
import be.mbolle.crochcounter.patterns.data.PatternRoomRepository
import be.mbolle.crochcounter.projects.data.ProjectRoomRepository

enum class SearchType {
    PROJECT, PATTERN
}

class ProjectViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ProjectViewModelFactory? = null

        fun getInstance(context: Context): ProjectViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ProjectViewModelFactory(
                    context,
                ).also { instance = it }
            }
        }
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // separate instances
        val database = CrochDatabase.getDatabase(context)
        val projectRepository = ProjectRoomRepository(database.crochDao())


        if (modelClass.isAssignableFrom(ProjectSearchableViewModel::class.java)) {
            return ProjectSearchableViewModel(
                projectRepository
            ) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}

class PatternViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: PatternViewModelFactory? = null

        fun getInstance(context: Context): PatternViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: PatternViewModelFactory(
                    context,
                ).also { instance = it }
            }
        }
    }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // separate instances
        val database = CrochDatabase.getDatabase(context)
        val patternRepository = PatternRoomRepository(database.patternDao())

        if (modelClass.isAssignableFrom(PatternSearchableViewModel::class.java)) {
            return PatternSearchableViewModel(
                patternRepository = patternRepository,
            ) as T

        }

        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}

class GenericViewModelFactory(private val context: Context, private val searchType: SearchType) :
    ViewModelProvider.Factory {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: GenericViewModelFactory? = null

        fun getInstance(context: Context): GenericViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: GenericViewModelFactory(
                    context,
                    searchType = SearchType.PROJECT
                ).also { instance = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // separate instances
        val database = CrochDatabase.getDatabase(context)

        val projectRepository = ProjectRoomRepository(database.crochDao())
        val patternRepository = PatternRoomRepository(database.patternDao())

        if (modelClass.isAssignableFrom(ProjectSearchableViewModel::class.java)) {
            return ProjectSearchableViewModel(projectRepository) as T
        } else if (
            modelClass.isAssignableFrom(ProjectSearchableViewModel::class.java)
        ) {
            return PatternSearchableViewModel(patternRepository) as T
        }
        throw IllegalStateException("nope")
    }
}