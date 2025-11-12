package be.mbolle.crochcounter.core.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.core.model.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class ProjectSearchableViewModel(
    private val projectRepository: ProjectRepository
) : SearchableViewModel() {

    init {
        init()
    }

    override fun init() {
        viewModelScope.launch {
            val projects = projectRepository.getAllProjects()
            Timber.log(Log.WARN, projects.toString())
            items.value = projects.map { it.name }
            filteredItems = items
        }
    }
}

open class PatternSearchableViewModel(
    private val patternRepository: PatternRepository
) : SearchableViewModel() {

    init {
        init()
    }
    override fun init() {
        viewModelScope.launch {
            val projects = patternRepository.getAllPatterns()
            Timber.log(Log.WARN, projects.toString())
            items.value = projects.map { it.name }
            filteredItems = items
        }
    }
}

abstract class SearchableViewModel(
) : ViewModel() {
    var items: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
        private set

    var filteredItems: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
        internal set

    var searchName: TextFieldState = TextFieldState()
        private set

    init {
        // initial projectItems from data storage.
    }

    // should be abstract?
//     fun init() {
//        viewModelScope.launch {
//            val projects = projectRepository.getAllProjects()
//            Timber.log(Log.WARN, projects.toString())
//            items.value = projects.map { it.name }
//            filteredItems = items
//        }
//    }

    abstract fun init()

    suspend fun run() {
        Log.d("SearchProjectVM", "effect is runnable.")
        snapshotFlow { searchName.text }
            .collectLatest { searchResult ->
                filterProjects(searchResult.toString())
            }
    }

    private fun filterProjects(projectName: String) {
        filteredItems.value =
            items.value.filter { it.contains(projectName, ignoreCase = true) }
    }
}