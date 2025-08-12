package be.mbolle.crochcounter.projects.data

interface CounterRepository {
    suspend fun setProjectInactive(project: String)
    suspend fun setProjectActive(project: String)
    suspend fun getAllProjects(): List<CrochProject?>
    suspend fun getActiveProject(): CrochProject
    suspend fun deleteProject(project: String)
    suspend fun createProject(project: String)
    suspend fun renameProject(oldName: String, newName: String)

    suspend fun getInfoFromProject(project: String): CrochProject
    suspend fun increaseCounterOfProject(project: String, value: Int)
    suspend fun decreaseCounterOfProject(project: String, value: Int)
    suspend fun resetCounterOfProject(project: String)
}

class CounterRoomRepository(private val crochDao: CrochDao): CounterRepository {
    override suspend fun setProjectInactive(project: String) {
        crochDao.editActiveProject(false, project)
    }

    override suspend fun setProjectActive(project: String) {
        crochDao.editActiveProject(true, project)
    }

    override suspend fun getAllProjects(): List<CrochProject?> {
        return crochDao.getAllProjects()
    }

    override suspend fun getActiveProject(): CrochProject {
        return crochDao.getActiveProject()
    }

    override suspend fun getInfoFromProject(project: String): CrochProject {
        return crochDao.getProject(project)
    }

    override suspend fun increaseCounterOfProject(project: String, value: Int) {
        return crochDao.editProject(value+1, project)
    }

    override suspend fun decreaseCounterOfProject(project: String, value: Int) {
        return crochDao.editProject(value-1, project)
    }

    override suspend fun resetCounterOfProject(project: String) {
        return crochDao.editProject(0, project)
    }

    override suspend fun createProject(project: String) {
        crochDao.createProject(CrochProject(name = project, value = 0, isActive = true))
    }

    override suspend fun deleteProject(project: String) {
        crochDao.deleteProject(name = project)
    }

    override suspend fun renameProject(oldName: String, newName: String) {
        crochDao.editProjectName(oldName, newName)
    }
}