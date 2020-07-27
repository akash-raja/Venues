package com.example.venues.ui

import android.content.Context
import androidx.room.*
import com.example.venues.api.FourSquareService
import com.example.venues.data.VenueSearchResponse
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Entity
data class VenueDataStore(
    @PrimaryKey val id: String,
    val searchString: String,
    val title: String,
    val address: String
)

@Dao
interface VenueDao {

    @Update(entity = VenueDataStore::class)
    suspend fun updateVenue(venue: VenueDataStore)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venue: VenueDataStore)

    @Query("Select * from VenueDataStore where searchString = :search")
    fun loadVenue(search: String): Observable<List<VenueDataStore>>

    @Query("Select * from VenueDataStore where searchString = :search")
    suspend fun selectVenueStored(search: String): List<VenueDataStore>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(venue: List<VenueDataStore>)
}

@Database(entities = [VenueDataStore::class], version = 1, exportSchema = false)
public abstract class VenueDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao

    companion object {

        @Volatile
        private var INSTANCE: VenueDatabase? = null

        fun getDatabase(context: Context): VenueDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VenueDatabase::class.java,
                    "venue_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

class VenueRepository(private val dao: VenueDao, private val fourSquareService: FourSquareService) {

    private suspend fun fetchFromNetwork(search: String) {
        withContext(Dispatchers.IO) {
            val venues = fourSquareService.searchVenues(search)
            dao.insertList(venues.response.venues.map {
                VenueDataStore(
                    it.id,
                    search.toLowerCase(),
                    it.name,
                    it.location.formattedAddress.toString()
                )
            })
        }
    }

    suspend fun canLoadData(search: String, isNetworkAvailable: Boolean): Boolean {
        val data = dao.selectVenueStored(search)
        if (isNetworkAvailable || data.isNotEmpty()) {
            fetchFromNetwork(search)
            return true
        }
        return false
    }

    fun getDataForLocation(search: String): Observable<List<VenueDataStore>> {
        return dao.loadVenue(search)
    }
}