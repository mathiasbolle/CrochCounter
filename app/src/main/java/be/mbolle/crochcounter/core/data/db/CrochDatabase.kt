package be.mbolle.crochcounter.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.mbolle.crochcounter.patterns.data.Pattern
import be.mbolle.crochcounter.patterns.data.PatternDao
import be.mbolle.crochcounter.patterns.data.PatternItem
import be.mbolle.crochcounter.patterns.data.PatternItemDao
import be.mbolle.crochcounter.patterns.data.PatternPatternItemDetail
import be.mbolle.crochcounter.patterns.data.PatternWithPatternItemDao
import be.mbolle.crochcounter.projects.data.ProjectDao
import be.mbolle.crochcounter.projects.data.Project

@Database(
    entities = [Project::class, Pattern::class, PatternProjectDetails::class,
         PatternItem::class],
    version = 1
)
//@TypeConverters(PatternItemKindConverter::class)
abstract class CrochDatabase : RoomDatabase() {
    // TO BE IMPLEMENTED
    abstract fun crochDao(): ProjectDao
    abstract fun projectWithPatternDao(): ProjectWithPatternDao
    abstract fun patternDao(): PatternDao

    abstract fun patternItemDao(): PatternItemDao
    abstract fun patternWithPatternItemDao(): PatternWithPatternItemDao

    companion object {
        @Volatile
        private var INSTANCE: CrochDatabase? = null

        fun getDatabase(context: Context): CrochDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                CrochDatabase::class.java,
                "croch_db.db"
            )
                .createFromAsset("database/project_db.db")
                .build()



            INSTANCE = instance
            instance
        }
    }
}