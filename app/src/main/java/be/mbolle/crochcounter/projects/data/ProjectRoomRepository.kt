package be.mbolle.crochcounter.projects.data

import android.util.Log
import be.mbolle.crochcounter.core.model.ProjectRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

fun Project.toModel(): be.mbolle.crochcounter.projects.model.Project {
    return be.mbolle.crochcounter.projects.model.Project(
        name = this.name,
        counter = this.value
    )
}

class ProjectRoomRepository(private val projectDao: ProjectDao) : ProjectRepository {

    override suspend fun getAllProjects(): List<be.mbolle.crochcounter.projects.model.Project> {
        return projectDao.getAllProjects().map { it.toModel() }
    }

    /**
     * Pattern should be created before a project
     */
    override suspend fun createProject(project: String): Project? {
        val projectId =
            projectDao.createProject(Project(name = project, value = 0, isActive = true))

        return projectDao.getProjectById(projectId.toInt())
    }

    override suspend fun setProjectInactive(project: String) {
        projectDao.editActiveProject(false, project)
    }

    override suspend fun setProjectActive(project: String) {
        projectDao.editActiveProject(true, project)
    }

    override suspend fun getActiveProject(): Flow<Project?> {
        return projectDao.getActiveProject()
    }

    override suspend fun getInfoFromProject(project: String): Project {
        return projectDao.getProject(project)
    }

    override suspend fun increaseCounterOfProject(project: String, value: Int) {
        return projectDao.editProject(value + 1, project)
    }

    override suspend fun decreaseCounterOfProject(project: String, value: Int) {
        return projectDao.editProject(value - 1, project)
    }

    override suspend fun resetCounterOfProject(project: String) {
        return projectDao.editProject(0, project)
    }

    override suspend fun deleteProject(project: String) {
        projectDao.deleteProject(name = project)
    }

    override suspend fun renameProject(oldName: String, newName: String) {
        projectDao.editProjectName(oldName, newName)
    }
}