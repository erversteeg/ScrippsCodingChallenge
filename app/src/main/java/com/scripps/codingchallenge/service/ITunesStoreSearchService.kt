package com.scripps.codingchallenge.service

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesStoreSearchService {

    @GET("/search?country=US&limit=10")
    fun getResults(@Query(value = "term", encoded = true) term: String): Call<JsonElement>
}