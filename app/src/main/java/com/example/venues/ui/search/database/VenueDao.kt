package com.example.venues.ui.search.database

import androidx.room.*
import io.reactivex.Observable

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