package be.mbolle.crochcounter.core.model

import be.mbolle.crochcounter.projects.data.Project
import kotlinx.coroutines.flow.Flow

/**
 * The implementation of this repository contains the
 */
interface ProjectRepository {
    suspend fun setProjectInactive(project: String)
    suspend fun setProjectActive(project: String)
    suspend fun getAllProjects(): List<be.mbolle.crochcounter.projects.model.Project>
    suspend fun getActiveProject(): Flow<Project?>
    suspend fun deleteProject(project: String)
    suspend fun createProject(project: String): Project?
    suspend fun renameProject(oldName: String, newName: String)

    suspend fun getInfoFromProject(project: String): Project
    suspend fun increaseCounterOfProject(project: String, value: Int)
    suspend fun decreaseCounterOfProject(project: String, value: Int)
    suspend fun resetCounterOfProject(project: String)
}
