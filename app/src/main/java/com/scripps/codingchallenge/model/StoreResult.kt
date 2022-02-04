package com.scripps.codingchallenge.model

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale


class StoreResult(
    val uid: Int,
    val wrapperType: String?,
    var kind: String?,
    val artistId: Int?,
    val collectionId: Int?,
    val trackId: Int?,
    val artistName: String?,
    val collectionName: String?,
    val trackName: String?,
    val collectionCensoredName: String?,
    val trackCensoredName: String?,
    val artistViewUrl: String?,
    val collectionViewUrl: String?,
    val trackViewUrl: String?,
    val previewUrl: String?,
    val artworkUrl30: String?,
    val artworkUrl60: String?,
    val artworkUrl100: String?,
    val collectionPrice: Float?,
    val trackPrice: Float?,
    var releaseDate: String?,
    val collectionExplicitness: String?,
    val trackExplicitness: String?,
    val discCount: Int?,
    val discNumber: Int?,
    var trackCount: Int?,
    var trackNumber: Int?,
    var trackTimeMillis: Int?,
    val country: String?,
    val currency: String?,
    val primaryGenreName: String?,
    val isStreamable: Boolean?
) {
    private val inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val displayDateFormat = "MM/dd/yyyy"

    fun showTrackCount(): Boolean {
        // true because kind can be true, false, or null
        return trackCount != null && kind?.contains("movie") != true
    }

    fun showTrackNumber(): Boolean {
        return trackNumber != null && kind?.contains("movie") != true
    }

    fun getFormattedTrackCount(): String {
        if (trackCount == null) return ""

        return NumberFormat.getInstance(Locale.US).format(trackCount)
    }

    fun getDisplayReleaseDate(): String {
        if (releaseDate == null) return ""

        val inputFormat = SimpleDateFormat(inputDateFormat, Locale.US)
        val date = inputFormat.parse(releaseDate)!!

        return SimpleDateFormat(displayDateFormat, Locale.US).format(date)
    }

    fun getTrackLengthMinutes(): Int {
        if (trackTimeMillis == null) return 0

        val totalSeconds = trackTimeMillis!! / 1000
        return totalSeconds / 60
    }

    fun getTrackLengthSeconds(): Int {
        if (trackTimeMillis == null) return 0

        val totalSeconds = trackTimeMillis!! / 1000
        return totalSeconds % 60
    }

    companion object {
        var uid = 0
    }
}