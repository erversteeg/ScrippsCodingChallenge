package com.scripps.codingchallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.scripps.codingchallenge.databinding.FragmentStoreResultDetailsBinding

class StoreResultDetailsFragment : Fragment() {

    private var _binding: FragmentStoreResultDetailsBinding? = null
    private val binding get() = _binding!!

    private var storeResultId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            storeResultId = getInt("store_result_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreResultDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(StoreResultsViewModel::class.java)
        val storeResult = viewModel.getStoreResult(storeResultId)!!

        Glide.with(this).load(storeResult.artworkUrl100).into(binding.imageViewArtwork)

        binding.textViewTrackName.text = storeResult.trackName
        binding.textViewCollectionName.text = storeResult.collectionName
        binding.textViewArtistName.text = storeResult.artistName

        binding.textViewPrice.text = String.format("$%.2f", storeResult.trackPrice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}