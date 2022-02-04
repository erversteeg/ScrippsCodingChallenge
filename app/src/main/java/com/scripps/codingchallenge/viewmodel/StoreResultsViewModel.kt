package com.scripps.codingchallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.scripps.codingchallenge.model.StoreResult
import com.scripps.codingchallenge.repository.StoreResultsRepository

class StoreResultsViewModel(application: Application): AndroidViewModel(application) {

    private val repository by lazy { StoreResultsRepository() }

    var term: String? = null
    var storeResults: LiveData<List<StoreResult>> = repository.storeResultsData

    val errorData: LiveData<Throwable?> = repository.errorData

    fun getStoreResults(term: String) {
        repository.getStoreResults(term)
        this.term = term
    }

    fun getStoreResult(uid: Int): StoreResult? {
        storeResults.value?.apply {
            for (storeResult in this) {
                if (storeResult.uid == uid) {
                    return storeResult
                }
            }
        }

        return null
    }
}