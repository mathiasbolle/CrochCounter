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
    suspend fun getCounter(): Int
    suspend fun incrementBy(value: Int)
    suspend fun decreaseBy(value: Int)
    suspend fun reset()
}

class CounterDatastoreRepository(val context: Context): CounterRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "counter")

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