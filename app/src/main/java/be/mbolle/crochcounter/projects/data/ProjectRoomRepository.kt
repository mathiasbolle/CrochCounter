package be.mbolle.crochcounter.projects.data

import be.mbolle.crochcounter.core.model.CounterRepository


class CounterRoomRepository(private val projectDao: ProjectDao): CounterRepository {
    override suspend fun setProjectInactive(project: String) {
        projectDao.editActiveProject(false, project)
    }

    override suspend fun setProjectActive(project: String) {
        projectDao.editActiveProject(true, project)
    }

    override suspend fun getAllProjects(): List<Project?> {
        return projectDao.getAllProjects()
    }

    override suspend fun getActiveProject(): Project {
        return projectDao.getActiveProject()
    }

    override suspend fun getInfoFromProject(project: String): Project {
        return projectDao.getProject(project)
    }

    override suspend fun increaseCounterOfProject(project: String, value: Int) {
        return projectDao.editProject(value+1, project)
    }

    override suspend fun decreaseCounterOfProject(project: String, value: Int) {
        return projectDao.editProject(value-1, project)
    }

    override suspend fun resetCounterOfProject(project: String) {
        return projectDao.editProject(0, project)
    }

    override suspend fun createProject(project: String) {
        projectDao.createProject(Project(name = project, value = 0, isActive = true))
    }

    override suspend fun deleteProject(project: String) {
        projectDao.deleteProject(name = project)
    }

    override suspend fun renameProject(oldName: String, newName: String) {
        projectDao.editProjectName(oldName, newName)
    }
}