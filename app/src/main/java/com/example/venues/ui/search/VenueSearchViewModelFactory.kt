package com.example.venues.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.venues.ui.VenueDatabase

class VenueSearchViewModelFactory(private val applicationContext: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueSearchViewModel::class.java)) {
            return VenueSearchViewModel(venueDatabase = VenueDatabase.getDatabase(applicationContext)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}