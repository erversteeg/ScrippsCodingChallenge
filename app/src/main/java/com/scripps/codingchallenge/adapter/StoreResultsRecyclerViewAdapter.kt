package com.scripps.codingchallenge.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scripps.codingchallenge.R
import com.scripps.codingchallenge.StoreResultsViewModel
import com.scripps.codingchallenge.model.StoreResult

class StoreResultsRecyclerViewAdapter(private val fragment: Fragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewModel: StoreResultsViewModel = ViewModelProvider(fragment).get(StoreResultsViewModel::class.java)

    var storeResults = emptyList<StoreResult>()

    private val viewTypeStoreResult = 1

    init {

        viewModel.storeResults.observe(fragment.viewLifecycleOwner) { storeResults ->
            this.storeResults = storeResults

            // TODO: Add update, change, and other notify methods
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_store_result_cell, parent, false)
        return StoreResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storeResultViewHolder = holder as StoreResultViewHolder

        val storeResult = storeResults[position]

        Glide.with(fragment.requireContext()).load(storeResult.artworkUrl100 ?: "")
            .into(storeResultViewHolder.artworkImageView)

        storeResultViewHolder.trackName.text = storeResult.trackName ?: ""
        storeResultViewHolder.collectionName.text = storeResult.collectionName ?: ""
        storeResultViewHolder.artistName.text = storeResult.artistName ?: ""
    }

    override fun getItemCount(): Int {
        return storeResults.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeStoreResult
    }

    class StoreResultViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val artworkImageView: ImageView = view.findViewById(R.id.image_view_artwork)

        val collectionName: TextView = view.findViewById(R.id.text_view_collection_name)
        val trackName: TextView = view.findViewById(R.id.text_view_track_name)
        val artistName: TextView = view.findViewById(R.id.text_view_artist_name)
    }
}