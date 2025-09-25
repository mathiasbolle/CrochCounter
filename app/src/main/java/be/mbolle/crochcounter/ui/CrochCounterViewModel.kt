package be.mbolle.crochcounter.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.core.presentation.navigation.ProjectScreenNav
import be.mbolle.crochcounter.projects.model.use_cases.CreateProjectUseCase
import be.mbolle.crochcounter.projects.model.use_cases.GetActiveProjectUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import timber.log.Timber

class CrochCounterViewModel(
    private val projectRepository: ProjectRepository,
    private val getActiveProjectUseCase: GetActiveProjectUseCase,
) : ViewModel() {

    // this probably breaks the single responsiblity principle:
    var isOverlayEnabled by mutableStateOf(false)
        private set

    fun enableOverlayService(value: Boolean) {
        isOverlayEnabled = value
    }

    // wrap the state in a sealed class
    var crochCounterState: CrochCounterProjectState by mutableStateOf(CrochCounterProjectState.Loading)
        private set

    private var succeedState: CrochCounterProjectState.Succes? = null


    init {
        loadSuccessState()
    }

    private fun loadSuccessState() {
        viewModelScope.launch {
            getActiveProjectUseCase.invoke()
                .catch { exception ->
                    Timber.log(Log.ERROR, exception)
                    errorHandlingState(exception)
                }
                .collect {
                    crochCounterState = CrochCounterProjectState.Succes(
                        counter = it.counter.toString(),
                        projectTitle = it.name,
                        patterns = emptyList()
                    )
                }
        }
    }

    private fun errorHandlingState(exception: Throwable) {
        if (crochCounterState !is CrochCounterProjectState.Succes) {
            crochCounterState = CrochCounterProjectState.Error(
                "The following error occurred:\n" +
                        exception.message + "\n" +
                        "You should ask Pookie for help."
            )
            Timber.e("Invalid state while performing a state modification")
        } else {
            Timber.i("State is valid")
        }
    }

    // should probably be removed by another VM
//    fun addProject(project: String) {
//        initialStateLoaded()
//        viewModelScope.launch {
//            val project = createProjectUseCase(1) // fixed
//        }
//    }


    // you should rewrite this
//    fun refreshCache() {
//        viewModelScope.launch {
//            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
//            Log.d("CrochCounterViewModel", counterRepository.getAllProjects().toString())
//            if (counterRepository.getAllProjects().isNotEmpty()) {
//                crochCounterState = CrochCounterState(
//                    name = counterRepository.getActiveProject().name,
//                    counter = counterRepository.getActiveProject().value,
//                    list = emptyList()
//                )
//            } else {
//                crochCounterState = CrochCounterState()
//
//            }
//        }
//    }

    fun addCounterByOne() {
        viewModelScope.launch {
            crochCounterState.let {
                Timber.log(Log.WARN, "the succeedState is $succeedState")
                val succes = (crochCounterState as CrochCounterProjectState.Succes)
                Timber.log(Log.WARN, crochCounterState.toString())
                Timber.log(Log.WARN, "addCounterByOne triggered!")
                projectRepository.increaseCounterOfProject(
                    succes.projectTitle,
                    succes.counter.toInt()
                )
            }
            //refresh state
            //refreshCache()
        }
    }

    fun makeProjectVisible(name: String) {
        val succes = (crochCounterState as CrochCounterProjectState.Succes)
        viewModelScope.launch {
            projectRepository.setProjectInactive(succes.projectTitle)
            projectRepository.setProjectActive(name)
        }
    }

    fun subtractCounterByOne() {

        viewModelScope.launch {
            crochCounterState.let {
                Timber.log(Log.WARN, "the succeedState is $succeedState")
                val succes = (crochCounterState as CrochCounterProjectState.Succes)
                Timber.log(Log.WARN, crochCounterState.toString())
                Timber.log(Log.WARN, "addCounterByOne triggered!")
                projectRepository.decreaseCounterOfProject(
                    succes.projectTitle,
                    succes.counter.toInt()
                )
            }
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            crochCounterState.let {
                val succes = (crochCounterState as CrochCounterProjectState.Succes)
                projectRepository.resetCounterOfProject(succes.projectTitle)
            }
            //refreshCache()
        }
    }

    fun removeProject() {
        Log.d("CrochCounterViewModel", "remove project..")
        viewModelScope.launch {

        }
    }


    fun renameProject(newName: String) {
        viewModelScope.launch {
            //projectRepository.renameProject(crochCounterState.name!!, newName)
            //refreshCache()
        }
    }

    fun switchProject(oldProject: String, newProject: String) {
        viewModelScope.launch {
            projectRepository.setProjectInactive(oldProject)
            projectRepository.setProjectActive(newProject)

//            refreshCache()
        }
    }
}