package be.mbolle.crochcounter.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface CounterRepository {
    suspend fun setProjectInactive(project: String)
    suspend fun setProjectActive(project: String)
    suspend fun getAllProjects(): List<CrochProject>
    suspend fun getActiveProject(): CrochProject
    suspend fun deleteProject(project: String)
    suspend fun createProject(project: String)
    suspend fun renameProject(oldName: String, newName: String)

    suspend fun getInfoFromProject(project: String): CrochProject
    suspend fun increaseCounterOfProject(project: String, value: Int)
    suspend fun decreaseCounterOfProject(project: String, value: Int)
    suspend fun resetCounterOfProject(project: String)
}

/*
class CounterDatastoreRepository private constructor(val context: Context): CounterRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "counter")

    companion object {

        @Volatile
        private var instance: CounterRepository? = null

        fun getInstance(context: Context): CounterRepository {
            return instance ?: synchronized(this) {
                instance
                    ?: CounterDatastoreRepository(context).also { instance = it }
            }
        }

    }

    private val counterKey = intPreferencesKey("counterValue")
    override suspend fun getCounter(): Int {
        val flow = context.dataStore.data.map { preferences ->
            preferences[counterKey] ?: 0
        }

        return flow.first()
    }

    override suspend fun incrementBy(value: Int) {
        context.dataStore.edit { counter ->
            val currentCounter = counter[counterKey] ?: 0

            counter[counterKey] = currentCounter+value
        }
    }

    override suspend fun decreaseBy(value: Int) {
        context.dataStore.edit { counter ->
            val currentCounter = counter[counterKey] ?: 0

            counter[counterKey] = currentCounter-value
        }
    }

    override suspend fun reset() {
        context.dataStore.edit { counter ->
            counter[counterKey] = 0
        }
    }
}

 */

class CounterRoomRepository(private val crochDao: CrochDao): CounterRepository {
    override suspend fun setProjectInactive(project: String) {
        crochDao.editActiveProject(false, project)
    }

    override suspend fun setProjectActive(project: String) {
        crochDao.editActiveProject(true, project)
    }

    override suspend fun getAllProjects(): List<CrochProject> {
        return crochDao.getAllProjects()
    }

    override suspend fun getActiveProject(): CrochProject {
        return crochDao.getActiveProject()
    }

    override suspend fun getInfoFromProject(project: String): CrochProject {
        //crochDao.editProject()
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
        crochDao.deleteProject(CrochProject(name = project))
    }

    override suspend fun renameProject(oldName: String, newName: String) {
        crochDao.editProjectName(oldName, newName)
    }
}