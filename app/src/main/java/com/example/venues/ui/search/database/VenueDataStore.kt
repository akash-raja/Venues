package com.example.venues.ui.search.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VenueDataStore(
    @PrimaryKey val id: String,
    val searchString: String,
    val title: String,
    val address: String
)