package be.mbolle.crochcounter.projects.model.use_cases

import android.util.Log
import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.projects.model.Project
import be.mbolle.crochcounter.projects.model.exceptions.NoActiveProjectException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class GetActiveProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(): Flow<Project> = runBlocking {
        Timber.log(Log.WARN, "The current project:")
        //Timber.log(Log.WARN, projectRepository.getActiveProject().toList().toString())

        projectRepository.getActiveProject().transform { value ->
            if (value == null) {
                throw NoActiveProjectException()
            }

            emit(value)
        }.map { it ->
            Project(
                name = it.name,
                counter = it.value
            )
        }
    }
}