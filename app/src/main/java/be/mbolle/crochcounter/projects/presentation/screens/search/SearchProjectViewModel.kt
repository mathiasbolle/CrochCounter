package be.mbolle.crochcounter.projects.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class SearchProjectViewModel : ViewModel() {
    val data = listOf("test1", "test2")
    var projectItems:
            MutableStateFlow<List<String>> =
        MutableStateFlow(data) // get all the projects that are stored in the db
        private set

    var filteredItems: MutableStateFlow<List<String>> = projectItems

    var searchProjectName: TextFieldState = TextFieldState()
        private set

    init {
        // initial projectItems from data storage.
    }

    suspend fun run() {
        Log.d("SearchProjectVM", "effect is runnable.")
        snapshotFlow { searchProjectName.text }
            .collectLatest { searchResult ->
                filterProjects(searchResult.toString())
            }
    }

    private fun filterProjects(projectName: String) {
        filteredItems.value = data.filter { it.contains(projectName, ignoreCase = true) }
    }
}