package com.example.venues.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.venues.api.FourSquareService
import com.example.venues.ui.search.database.VenueDatabase
import javax.inject.Inject

class VenueSearchViewModelFactory @Inject constructor(private val venueDatabase: VenueDatabase, private val fourSquareService: FourSquareService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueSearchViewModel::class.java)) {
            return VenueSearchViewModel(venueDatabase = (venueDatabase), fourSquareService = fourSquareService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}