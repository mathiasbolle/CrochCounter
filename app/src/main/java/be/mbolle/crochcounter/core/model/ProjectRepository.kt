package be.mbolle.crochcounter.core.model

import be.mbolle.crochcounter.projects.data.Project

interface CounterRepository {
    suspend fun setProjectInactive(project: String)
    suspend fun setProjectActive(project: String)
    suspend fun getAllProjects(): List<Project?>
    suspend fun getActiveProject(): Project
    suspend fun deleteProject(project: String)
    suspend fun createProject(project: String)
    suspend fun renameProject(oldName: String, newName: String)

    suspend fun getInfoFromProject(project: String): Project
    suspend fun increaseCounterOfProject(project: String, value: Int)
    suspend fun decreaseCounterOfProject(project: String, value: Int)
    suspend fun resetCounterOfProject(project: String)
}
