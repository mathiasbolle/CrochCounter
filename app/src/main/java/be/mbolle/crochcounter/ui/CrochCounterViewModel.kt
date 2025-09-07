package be.mbolle.crochcounter.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.projects.model.use_cases.CreateProjectUseCase
import kotlinx.coroutines.launch

class CrochCounterViewModel(val counterRepository: ProjectRepository, val createProjectUseCase: CreateProjectUseCase) : ViewModel() {

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
            createProjectUseCase(1) // fixed

            if (crochCounterState.name != null) {
                switchProject(oldProject = crochCounterState.name!!, project)
            } else {
                refreshCache()
            }
        }
    }


    fun makeEditProjectInvisible() {
        crochCounterState = crochCounterState.copy(
            editProjectState = crochCounterState.editProjectState.copy(isVisible = false)
        )
    }

    fun setTitleEditDialog(title: String) {
        crochCounterState =
            crochCounterState.copy(editProjectState = crochCounterState.editProjectState.copy(text = title))
    }

    fun makeCreateProjectDialogVisible() {
        Log.d("CrochCounterViewModel", crochCounterState.createProjectState.toString())
        crochCounterState = crochCounterState.copy(
            createProjectState = crochCounterState.createProjectState.copy(isVisible = true)
        )
    }

    fun makeEditProjectDialogVisible() {
        Log.d("CrochCounterViewModel2", crochCounterState.editProjectState.toString())

        crochCounterState = crochCounterState.copy(
            editProjectState = crochCounterState.editProjectState.copy(isVisible = true)
        )

    }

    fun makeCreateProjectDialogInvisible() {
        crochCounterState = crochCounterState.copy(
            createProjectState = crochCounterState.createProjectState.copy(isVisible = false)
        )
    }


    fun setTitleProjectDialog(title: String) {
        crochCounterState = crochCounterState.copy(
            createProjectState = crochCounterState.createProjectState.copy(text = title)
        )
    }


    fun refreshCache() {
        viewModelScope.launch {
            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
            if (counterRepository.getAllProjects().isNotEmpty()) {
                crochCounterState = CrochCounterState(
                    name = counterRepository.getActiveProject().name,
                    counter = counterRepository.getActiveProject().value,
                    list = emptyList()
                )
            } else {
                crochCounterState = CrochCounterState()

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
        Log.d("CrochCounterViewModel", "remove project..")
        viewModelScope.launch {
            counterRepository.deleteProject(crochCounterState.name!!)
            if (crochCounterState.list.size > 1) {
                val randomProject = counterRepository.getAllProjects().random()?.name
                counterRepository.setProjectActive(randomProject.toString())
            }
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