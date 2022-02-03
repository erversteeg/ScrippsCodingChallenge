package com.scripps.codingchallenge.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.scripps.codingchallenge.model.StoreResult
import com.scripps.codingchallenge.service.ITunesStoreSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreResultsRepository {

    val storeResultsData = MutableLiveData<List<StoreResult>>().apply { value = emptyList() }

    private val apiBaseUrl = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder().baseUrl(apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val searchResultsService = retrofit.create(ITunesStoreSearchService::class.java)

    fun getStoreResults(term: String) {
        searchResultsService.getResults(term).enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (!response.isSuccessful) {
                    storeResultsData.value = emptyList()
                    return
                }
                else if (response.body() == null) return
                else if (!response.body()!!.isJsonObject) return
                else if (!response.body()!!.asJsonObject.has("results")) return
                else if (!response.body()!!.asJsonObject.get("results").isJsonArray) return

                val results = response.body()!!.asJsonObject.get("results").asJsonArray

                val resultObjects: MutableList<StoreResult> = ArrayList()

                for (resultEle in results) {
                    val result = resultEle.asJsonObject

                    val wrapperType = result.get("wrapperType").asString
                    val kind = result.get("kind").asString
                    val artistId = result.get("artistId").asInt
                    val collectionId = result.get("collectionId").asInt
                    val trackId = result.get("trackId").asInt
                    val artistName = result.get("artistName").asString
                    val collectionName = result.get("collectionName").asString
                    val trackName = result.get("trackName").asString
                    val collectionCensoredName = result.get("collectionCensoredName").asString
                    val trackCensoredName = result.get("trackCensoredName").asString
                    val artistViewUrl = result.get("artistViewUrl").asString
                    val collectionViewUrl = result.get("collectionViewUrl").asString
                    val trackViewUrl = result.get("trackViewUrl").asString
                    val previewUrl = result.get("previewUrl").asString
                    val artworkUrl30 = result.get("artworkUrl30").asString
                    val artworkUrl60 = result.get("artworkUrl60").asString
                    val artworkUrl100 = result.get("artworkUrl100").asString
                    val collectionPrice = result.get("collectionPrice").asFloat
                    val trackPrice = result.get("trackPrice").asFloat
                    val releaseDate = result.get("releaseDate").asString
                    val collectionExplicitness = result.get("collectionExplicitness").asString
                    val trackExplicitness = result.get("trackExplicitness").asString
                    val discCount = result.get("discCount").asInt
                    val discNumber = result.get("discNumber").asInt
                    val trackCount = result.get("trackCount").asInt
                    val trackNumber = result.get("trackNumber").asInt
                    val trackTimeMillis = result.get("trackTimeMillis").asInt
                    val country = result.get("country").asString
                    val currency = result.get("currency").asString
                    val primaryGenreName = result.get("primaryGenreName").asString
                    val isStreamable = result.get("isStreamable").asBoolean

                    val storeResult = StoreResult(++StoreResult.uid, wrapperType, kind, artistId, collectionId,
                        trackId, artistName, collectionName, trackName, collectionCensoredName,
                        trackCensoredName, artistViewUrl, collectionViewUrl, trackViewUrl,
                        previewUrl, artworkUrl30, artworkUrl60, artworkUrl100, collectionPrice,
                        trackPrice, releaseDate, collectionExplicitness, trackExplicitness,
                        discCount, discNumber, trackCount, trackNumber, trackTimeMillis,
                        country, currency, primaryGenreName, isStreamable)

                    resultObjects.add(storeResult)
                }

                storeResultsData.value = resultObjects
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }
}