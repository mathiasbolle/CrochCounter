package be.mbolle.crochcounter.projects.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CrochDao {
    @Query("UPDATE projects SET is_active = :active WHERE name = :name")
    suspend fun editActiveProject(active: Boolean, name: String)

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<Project>

    @Query("SELECT * FROM projects WHERE is_active = 1 LIMIT 1")
    suspend fun getActiveProject(): Project

    @Query("SELECT * FROM projects WHERE name = :project")
    suspend fun getProject(project: String): Project

    @Query("DELETE FROM projects WHERE name = :name")
    suspend fun deleteProject(name: String)

    @Query("UPDATE projects SET value = :value WHERE name = :name")
    suspend fun editProject(value: Int, name: String)

    @Query("UPDATE projects SET name = :newName WHERE name = :oldName")
    suspend fun editProjectName(oldName: String, newName: String)

    @Insert
    suspend fun createProject(project: Project)
}