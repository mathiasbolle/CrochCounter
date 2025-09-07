package be.mbolle.crochcounter.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.patterns.data.PatternDao
import be.mbolle.crochcounter.projects.data.ProjectDao
import be.mbolle.crochcounter.projects.data.Project

@Database(entities = [Project::class, Pattern::class, PatternProjectDetails::class], version = 1)
abstract class CrochDatabase : RoomDatabase() {
    // TO BE IMPLEMENTED
    abstract fun crochDao(): ProjectDao
    abstract fun projectWithPatternDao(): ProjectWithPatternDao
    abstract fun patternDao(): PatternDao

    companion object {
        @Volatile
        private var INSTANCE: CrochDatabase? = null

        fun getDatabase(context: Context): CrochDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                CrochDatabase::class.java,
                "croch_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}