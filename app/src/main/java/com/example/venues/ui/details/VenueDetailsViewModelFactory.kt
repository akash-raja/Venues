package com.example.venues.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.venues.api.FourSquareService
import javax.inject.Inject

class VenueDetailsViewModelFactory @Inject constructor(private val fourSquareService: FourSquareService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueDetailsViewModel::class.java)) {
            return VenueDetailsViewModel(fourSquareService = fourSquareService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}