package id.dayplug.sampleprefdatastore.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preferences"

class DataStoreRepository(context: Context) {

    private object  PreferenceKeys{
        val name = preferencesKey<String>("my_name")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(name: String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.name] = name
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
            }
            else{
                throw exception
            }
        }
        .map { preferences ->
            val myName: String = preferences[PreferenceKeys.name] ?: "none"
            myName
        }
}