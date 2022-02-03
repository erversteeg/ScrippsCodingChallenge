package com.scripps.codingchallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.scripps.codingchallenge.model.StoreResult
import com.scripps.codingchallenge.repository.StoreResultsRepository

class StoreResultsViewModel(application: Application): AndroidViewModel(application) {

    private val repository by lazy { StoreResultsRepository() }

    val storeResults: LiveData<List<StoreResult>> = repository.storeResultsData

    fun getStoreResults(term: String) {
        repository.getStoreResults(term)
    }
}