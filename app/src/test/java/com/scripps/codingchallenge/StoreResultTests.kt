package com.scripps.codingchallenge

import com.scripps.codingchallenge.model.StoreResult
import org.junit.Before
import org.junit.Test

class StoreResultTests {

    private lateinit var storeResult: StoreResult

    @Test
    fun testShowTrackCount() {
        storeResult.trackCount = 10

        storeResult.kind = "feature-movie"
        assert(!storeResult.showTrackCount())

        storeResult.kind = "song"
        assert(storeResult.showTrackCount())

        storeResult.trackCount = null
        assert(!storeResult.showTrackCount())
    }

    @Test
    fun testShowTrackNumber() {
        storeResult.trackNumber = 10

        storeResult.kind = "feature-movie"
        assert(!storeResult.showTrackNumber())

        storeResult.kind = "song"
        assert(storeResult.showTrackNumber())

        storeResult.trackNumber = null
        assert(!storeResult.showTrackNumber())
    }

    @Test
    fun testFormattedTrackCount() {
        storeResult.trackCount = 1130
        assert(storeResult.getFormattedTrackCount() == "1,130")

        storeResult.trackCount = 0
        assert(storeResult.getFormattedTrackCount() == "0")

        storeResult.trackCount = null
        assert(storeResult.getFormattedTrackCount().isEmpty())
    }

    @Test
    fun testGetDisplayReleaseDate() {
        storeResult.releaseDate = "2000-01-01T00:00:00Z"
        assert(storeResult.getDisplayReleaseDate() == "01/01/2000")

        storeResult.releaseDate = null
        assert(storeResult.getDisplayReleaseDate().isEmpty())
    }

    @Test
    fun testGetTrackLengthMinutes() {
        storeResult.trackTimeMillis = 300000
        assert(storeResult.getTrackLengthMinutes() == 5)

        storeResult.trackTimeMillis = 400100
        assert(storeResult.getTrackLengthMinutes() == 6)

        storeResult.trackTimeMillis = null
        assert(storeResult.getTrackLengthMinutes() == 0)
    }

    @Test
    fun testGetTrackLengthSeconds() {
        storeResult.trackTimeMillis = 300000
        assert(storeResult.getTrackLengthSeconds() == 0)

        storeResult.trackTimeMillis = 400100
        assert(storeResult.getTrackLengthSeconds() == 40)

        storeResult.trackTimeMillis = null
        assert(storeResult.getTrackLengthSeconds() == 0)
    }

    @Before
    fun setup() {
        storeResult = StoreResult(0, null, "movie", 0, 0, 0, "", "", "",
            "", "", "", "", "", "", null, null, null,
            0F, 0F, "2003-11-07T08:00:00Z", null, null, 0, 0, 0,
            0, 560000, "US", null, null, false)
    }
}