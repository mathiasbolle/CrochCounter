package be.mbolle.crochcounter.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.mbolle.crochcounter.projects.data.CrochDao
import be.mbolle.crochcounter.projects.data.CrochProject

@Database(entities = [CrochProject::class], version = 1)
abstract class CrochDatabase : RoomDatabase() {
    // TO BE IMPLEMENTED
    abstract fun crochDao(): CrochDao

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