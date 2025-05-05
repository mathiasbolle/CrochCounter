package be.mbolle.crochcounter.ui.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.data.CounterRepository
import be.mbolle.crochcounter.model.CrochCounter
import be.mbolle.crochcounter.model.CrochCounterState
import kotlinx.coroutines.launch

class CrochCounterViewModel(val counterRepository: CounterRepository) : ViewModel() {
    var crochCounterState by mutableStateOf(CrochCounterState())
        private set

    var isOverlayEnabled by mutableStateOf(false)
        private set

    fun enableOverlayService(value: Boolean) {
        isOverlayEnabled = value
    }

    init {
        refreshCache()
    }


    fun addProject(project: String) {
        viewModelScope.launch {
            counterRepository.createProject(project)

            switchProject(oldProject = crochCounterState.name!!, project)
        }
    }

    fun makeCreateProjectDialogVisible() {
        Log.d("CrochCounterViewModel", crochCounterState.createProjectState.toString())
        crochCounterState = crochCounterState.copy(createProjectState = crochCounterState.createProjectState.copy(isVisible = true))
    }

    fun makeCreateProjectDialogInvisible() {
        crochCounterState = crochCounterState.copy(createProjectState = crochCounterState.createProjectState.copy(isVisible = false))
    }


    fun setTitleProjectDialog(title: String) {
        crochCounterState = crochCounterState.copy(createProjectState = crochCounterState.createProjectState.copy(text = title))
    }


    fun refreshCache() {
        viewModelScope.launch {
            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
            if (counterRepository.getAllProjects().isNotEmpty()) {
                crochCounterState = CrochCounterState(
                    name = counterRepository.getActiveProject().name,
                    counter = counterRepository.getActiveProject().value,
                    list = counterRepository.getAllProjects().map { projects ->
                        CrochCounter(
                            name = projects.name,
                            counter = projects.value,
                            id = projects.id
                        )
                    }
                )
            }
        }
    }

    fun addCounterByOne() {
        viewModelScope.launch {
            //do ROOM stuff
            counterRepository.increaseCounterOfProject(
                crochCounterState.name!!,
                crochCounterState.counter
            )

            //refresh state
            refreshCache()
        }
    }

    fun subtractCounterByOne() {
        viewModelScope.launch {
            counterRepository.decreaseCounterOfProject(
                crochCounterState.name!!,
                crochCounterState.counter
            )

            //refresh state
            refreshCache()
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            counterRepository.resetCounterOfProject(crochCounterState.name!!)

            refreshCache()
        }
    }

    fun removeProject() {
        viewModelScope.launch {
            counterRepository.deleteProject(crochCounterState.name!!)
            val randomProject = counterRepository.getAllProjects().random().name
            counterRepository.setProjectActive(randomProject)

            refreshCache()
        }
    }

    fun renameProject(newName: String) {
        viewModelScope.launch {
            counterRepository.renameProject(crochCounterState.name!!, newName)

            refreshCache()
        }
    }

    fun switchProject(oldProject: String, newProject: String) {
        viewModelScope.launch {
            counterRepository.setProjectInactive(oldProject)
            counterRepository.setProjectActive(newProject)

            refreshCache()
        }
    }
}
