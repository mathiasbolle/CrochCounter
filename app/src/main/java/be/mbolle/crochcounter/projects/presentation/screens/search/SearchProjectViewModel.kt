package be.mbolle.crochcounter.projects.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.core.model.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchProjectViewModel(
    private val projectRepository: ProjectRepository
) : ViewModel() {
    var projectItems: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
        private set

    var filteredItems: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
        private set

    var searchProjectName: TextFieldState = TextFieldState()
        private set

    init {
        // initial projectItems from data storage.
        init()
    }

    private fun init() {
        viewModelScope.launch {
            val projects = projectRepository.getAllProjects()
            Timber.log(Log.WARN, projects.toString())
            projectItems.value = projects.map { it.name }
            filteredItems = projectItems
        }
    }

    suspend fun run() {
        Log.d("SearchProjectVM", "effect is runnable.")
        snapshotFlow { searchProjectName.text }
            .collectLatest { searchResult ->
                filterProjects(searchResult.toString())
            }
    }

    private fun filterProjects(projectName: String) {
        filteredItems.value =
            projectItems.value.filter { it.contains(projectName, ignoreCase = true) }
    }
}