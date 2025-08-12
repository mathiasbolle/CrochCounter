package be.mbolle.crochcounter.projects.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)], tableName = "crochProjects")
class CrochProject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("value") val value: Int = 0,
    @ColumnInfo("is_active") val isActive: Boolean = false
)