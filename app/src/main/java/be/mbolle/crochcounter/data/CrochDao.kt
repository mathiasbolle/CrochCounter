package be.mbolle.crochcounter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CrochDao {
    @Query("UPDATE crochProjects SET is_active = :active WHERE name = :name")
    suspend fun editActiveProject(active: Boolean, name: String)

    @Query("SELECT * FROM crochProjects")
    suspend fun getAllProjects(): List<CrochProject>

    @Query("SELECT * FROM crochProjects WHERE is_active = 1 LIMIT 1")
    suspend fun getActiveProject(): CrochProject

    @Query("SELECT * FROM crochProjects WHERE name = :project")
    suspend fun getProject(project: String): CrochProject

    @Query("DELETE FROM crochProjects WHERE name = :name")
    suspend fun deleteProject(name: String)

    @Query("UPDATE crochProjects SET value = :value WHERE name = :name")
    suspend fun editProject(value: Int, name: String)

    @Query("UPDATE crochProjects SET name = :newName WHERE name = :oldName")
    suspend fun editProjectName(oldName: String, newName: String)

    @Insert
    suspend fun createProject(crochProject: CrochProject)
}