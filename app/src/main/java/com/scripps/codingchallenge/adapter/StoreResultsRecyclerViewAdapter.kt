package com.scripps.codingchallenge.adapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scripps.codingchallenge.R
import com.scripps.codingchallenge.viewmodel.StoreResultsViewModel
import com.scripps.codingchallenge.model.StoreResult

class StoreResultsRecyclerViewAdapter(private val activity: FragmentActivity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewModel: StoreResultsViewModel = ViewModelProvider(activity).get(
        StoreResultsViewModel::class.java)

    var storeResults = emptyList<StoreResult>()

    private val viewTypeSearchBar = 0
    private val viewTypeStoreResult = 1
    private val viewTypeError = 2

    private var restoreTerm = true

    enum class NetworkState {
        SUCCESS,
        ERROR
    }

    private var networkState = NetworkState.SUCCESS

    init {
        viewModel.storeResults.observe(activity) { storeResults ->
            networkState = NetworkState.SUCCESS

            this.storeResults = storeResults

            // TODO: Add update, change, and other notify methods
            notifyDataSetChanged()
        }

        viewModel.errorData.observe(activity) {
            if (it != null) {
                networkState = NetworkState.ERROR

                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == viewTypeSearchBar) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_search_bar_cell, parent, false)
            SearchBarViewHolder(view)
        } else if (viewType == viewTypeStoreResult) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_store_result_cell, parent, false)
            StoreResultViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_network_error_cell, parent, false)
            NetworkErrorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoreResultViewHolder) {
            val storeResult = storeResults[position - 1]

            Glide.with(activity).load(storeResult.artworkUrl100 ?: "")
                .into(holder.artworkImageView)

            holder.trackName.text = storeResult.trackName ?: ""
            holder.collectionName.text = storeResult.collectionName ?: ""
            holder.artistName.text = storeResult.artistName ?: ""

            holder.itemView.setOnClickListener {
                val navHost = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                val navController = navHost.navController
                navController.navigate(R.id.action_details_fragment, Bundle().apply {
                    putInt("store_result_id", storeResult.uid)
                })
            }
        }
        else if (holder is SearchBarViewHolder) {
            if (restoreTerm) {
                holder.searchInputEditText.setText(viewModel.term)
                restoreTerm = false
            }

            holder.searchInputEditText.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.apply {
                        viewModel.getStoreResults(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable?) { }
            })
        }
    }

    override fun getItemCount(): Int {
        return if (networkState == NetworkState.SUCCESS) {
            storeResults.size + 1
        }
        // search bar + network error
        else {
            2
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            viewTypeSearchBar
        } else {
            if (networkState == NetworkState.SUCCESS) {
                viewTypeStoreResult
            }
            else {
                viewTypeError
            }
        }
    }

    class SearchBarViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val searchInputEditText: EditText = view.findViewById(R.id.edit_text_search_input)
    }

    class StoreResultViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val artworkImageView: ImageView = view.findViewById(R.id.image_view_artwork)

        val collectionName: TextView = view.findViewById(R.id.text_view_collection_name)
        val trackName: TextView = view.findViewById(R.id.text_view_track_name)
        val artistName: TextView = view.findViewById(R.id.text_view_artist_name)
    }

    class NetworkErrorViewHolder(view: View): RecyclerView.ViewHolder(view)
}