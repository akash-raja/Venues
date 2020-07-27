package com.example.venues.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venues.api.FourSquareService
import kotlinx.coroutines.launch

class VenueDetailsViewModel(
    private val venueId: String,
    fourSquareService: FourSquareService = FourSquareService.create()
) : ViewModel() {

    val detailUIData = MutableLiveData<VenueDetailsUIState>()

    init {
        viewModelScope.launch {
            detailUIData.value = VenueDetailsUIState.Loading

            runCatching {
                val venueDetails = fourSquareService.getVenueDetails(venueId)
                val venue = venueDetails.response.venue
                val uiData = VenueDetailsUIModel(
                    title = venue.name ?: "",
                    description = venue.description ?: "",
                    rating = venue.likes?.summary ?: "",
                    address = venue.location?.formattedAddress.toString(),
                    contact = venue.contact.toString(),
                    photoUrl = "${venue.bestPhoto?.prefix}original${venue.bestPhoto?.suffix}"
                )
                detailUIData.value = VenueDetailsUIState.Success(uiData)


            }.onFailure {
                detailUIData.value = VenueDetailsUIState.Failed
            }
        }
    }
}

data class VenueDetailsUIModel(
    val title: String,
    val description: String,
    val rating: String,
    val contact: String,
    val address: String,
    val photoUrl: String
)

sealed class VenueDetailsUIState {
    object Loading : VenueDetailsUIState()
    class Success(val data: VenueDetailsUIModel) : VenueDetailsUIState()
    object Failed : VenueDetailsUIState()
}