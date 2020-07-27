package com.example.venues.data

data class VenueDetailsResponse(var response: Response) {

    data class Response(var venue: Venue) {
        data class Venue(
            var bestPhoto: BestPhoto?,
            var contact: Contact?,
            var description: String?,
            var id: String?,
            var likes: Likes?,
            var location: Location?,
            var name: String?
        )
    }
}

data class Contact(var twitter: String = "Not Available", var phone: String = "Not Available")
data class Likes(var count: Int?, var summary: String?)
data class BestPhoto(var id: String?, var prefix: String?, var suffix: String?)