package com.example.venues.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VenueDetailsViewModelFactory(private val venueId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueDetailsViewModel::class.java)) {
            return VenueDetailsViewModel(venueId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}