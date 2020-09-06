package com.example.venues.ui.search

import com.example.venues.api.FourSquareService
import com.example.venues.ui.search.database.VenueDao
import com.example.venues.ui.search.database.VenueDataStore
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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