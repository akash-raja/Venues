package com.example.venues.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.venues.api.FourSquareService
import com.example.venues.ui.search.database.VenueDataStore
import com.example.venues.ui.search.database.VenueDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class VenueSearchViewModel(
    fourSquareService: FourSquareService,
    venueDatabase: VenueDatabase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val repository = VenueRepository(venueDatabase.venueDao(), fourSquareService)
    private val _uiState = MutableLiveData<VenueSearchUIState>()
    val uiState: LiveData<VenueSearchUIState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.postValue(VenueSearchUIState.Failed)
    }

    fun search(search: String, isNetworkAvailable: Boolean) {
        val searchString = search.trim().toLowerCase()
        _uiState.postValue(VenueSearchUIState.Loading)
        viewModelScope.launch(exceptionHandler) {
            val canLoadData = repository.canLoadData(searchString, isNetworkAvailable)
            if (!canLoadData) {
                _uiState.postValue(VenueSearchUIState.Failed)
            }
        }

        compositeDisposable.add(repository.getDataForLocation(searchString).subscribe({
            if (it.isNotEmpty()) {
                _uiState.postValue(VenueSearchUIState.Success(it))
            }
        }, {
            _uiState.postValue(VenueSearchUIState.Failed)
        }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

sealed class VenueSearchUIState {
    object Loading : VenueSearchUIState()
    class Success(val venueList: List<VenueDataStore>) : VenueSearchUIState()
    object Failed : VenueSearchUIState()
}