package com.example.venues.data

data class VenueSearchResponse(var response: Response)
data class Response(var venues: List<Venue>)

data class Venue(var id: String, var location: Location, var name: String)
data class Location(var formattedAddress: List<String>)