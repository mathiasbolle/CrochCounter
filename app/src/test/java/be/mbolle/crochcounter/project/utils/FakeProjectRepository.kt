package be.mbolle.crochcounter.project

import be.mbolle.crochcounter.core.model.ProjectRepository
import be.mbolle.crochcounter.projects.data.toModel
import be.mbolle.crochcounter.projects.model.Project

class FakeProjectRepository(
    val datastore: MutableList<be.mbolle.crochcounter.projects.data.Project> = mutableListOf()
) : ProjectRepository {

    private fun findProjectByName(project: String): be.mbolle.crochcounter.projects.data.Project {
        val projectByName = datastore
            .find { it.name == project }

        return projectByName!!
    }

    internal fun findById(id: Int): be.mbolle.crochcounter.projects.data.Project {
        val projectByName = datastore
            .find { it.projectId == id }

        return projectByName!!
    }

    override suspend fun setProjectInactive(project: String) {
        val indexInactiveProject = datastore.indexOf(
            findProjectByName(project)
        )

        datastore[indexInactiveProject] = datastore[indexInactiveProject].copy(
            isActive = false
        )
    }

    override suspend fun setProjectActive(project: String) {
        val indexInactiveProject = datastore.indexOf(
            findProjectByName(project)
        )

        datastore[indexInactiveProject] = datastore[indexInactiveProject].copy(
            isActive = true
        )
    }

    override suspend fun getAllProjects(): List<Project> {
        return datastore.map { it.toModel() }
    }

    override suspend fun getActiveProject(): be.mbolle.crochcounter.projects.data.Project {
        return datastore.find { it.isActive == true }!!
    }


    override suspend fun deleteProject(project: String) {
        datastore.remove(
            findProjectByName(project)
        )
    }

    override suspend fun createProject(project: String): be.mbolle.crochcounter.projects.data.Project? {
        datastore.add(
            be.mbolle.crochcounter.projects.data.Project(
                datastore.size,
                project
            )
        )

        return datastore.last()
    }

    override suspend fun renameProject(oldName: String, newName: String) {
        val oldProject = findProjectByName(oldName)
        val newProject = oldProject.copy(name = newName)

        datastore[datastore.indexOf(oldProject)] = newProject
    }

    override suspend fun getInfoFromProject(project: String): be.mbolle.crochcounter.projects.data.Project {
        return datastore.single { it.name == project }
    }

    override suspend fun increaseCounterOfProject(project: String, value: Int) {
        val project = findProjectByName(project)
        val newProject = project.copy(
            value = value + 1
        )

        datastore[datastore.indexOf(project)] = newProject
    }

    override suspend fun decreaseCounterOfProject(project: String, value: Int) {
        val project = findProjectByName(project)
        val newProject = project.copy(
            value = value - 1
        )

        datastore[datastore.indexOf(project)] = newProject
    }

    override suspend fun resetCounterOfProject(project: String) {
        val project = findProjectByName(project)
        val newProject = project.copy(
            value = 0
        )

        datastore[datastore.indexOf(project)] = newProject
    }
}