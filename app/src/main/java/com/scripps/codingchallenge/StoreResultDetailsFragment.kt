package com.scripps.codingchallenge

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.scripps.codingchallenge.databinding.FragmentStoreResultDetailsBinding
import com.scripps.codingchallenge.model.StoreResult
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

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

        showTopSection(storeResult)
        showDetailsSection(storeResult)

        if (storeResult.trackViewUrl != null) {
            binding.constraintLayoutDetailsCardLayout.setOnClickListener {
                loadUrl(storeResult.trackViewUrl)
            }
        }
        else {
            if (storeResult.collectionViewUrl != null) {
                binding.constraintLayoutDetailsCardLayout.setOnClickListener {
                    loadUrl(storeResult.collectionViewUrl)
                }
            }
            else {
                binding.constraintLayoutDetailsCardLayout.background = ColorDrawable(Color.TRANSPARENT)
            }
        }
    }

    private fun showTopSection(storeResult: StoreResult) {
        // artwork
        Glide.with(this).load(storeResult.artworkUrl100).into(binding.imageViewArtwork)

        // track name
        binding.textViewTrackName.text = storeResult.trackName

        // collection name
        binding.textViewCollectionName.text = storeResult.collectionName

        // artist name
        binding.textViewArtistName.text = storeResult.artistName

        // track price
        binding.textViewPrice.text = getString(R.string.price_format, storeResult.trackPrice)
    }

    private fun showDetailsSection(storeResult: StoreResult) {
        // kind
        binding.textViewKind.text = storeResult.kind?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.US
            ) else it.toString()
        }

        // true because the result can be null
        if (storeResult.kind?.contains("movie") == true) {
            binding.textViewKind.text = getString(R.string.store_result_kind_movie)

            // hide track count and number for movie kind
            binding.textViewTrackCount.visibility = View.GONE
            binding.textViewTrackCountLabel.visibility = View.GONE

            binding.textViewTrackNumber.visibility = View.GONE
            binding.textViewTrackNumberLabel.visibility = View.GONE

            // rename track time to length
            binding.textViewTrackTimeLabel.text = getString(R.string.track_time_movie_label)
        }

        // collection price
        if (storeResult.collectionPrice != storeResult.trackPrice) {
            binding.textViewCollectionPrice.text = getString(R.string.price_format, storeResult.collectionPrice)
        }
        else {
            binding.textViewCollectionPriceLabel.visibility = View.GONE
            binding.textViewCollectionPrice.visibility = View.GONE
        }

        // release date
        storeResult.releaseDate?.apply {
            val inputFormat = SimpleDateFormat(getString(R.string.input_date_format), Locale.US)
            val date = inputFormat.parse(this)!!

            val displayFormat = SimpleDateFormat(getString(R.string.display_date_format), Locale.US)
            binding.textViewReleaseDate.text = displayFormat.format(date)
        }

        // disc number
        if (storeResult.discNumber != null) {
            binding.textViewDiscNumber.text = storeResult.discNumber.toString()
        }
        else {
            binding.textViewDiscNumber.visibility = View.GONE
            binding.textViewDiscNumberLabel.visibility = View.GONE
        }

        // track count
        if (storeResult.trackCount != null) {
            binding.textViewTrackCount.text = NumberFormat.getInstance(Locale.US).format(storeResult.trackCount)
        }
        else {
            binding.textViewTrackCount.visibility = View.GONE
            binding.textViewTrackCountLabel.visibility = View.GONE
        }

        // track number
        if (storeResult.trackNumber != null) {
            binding.textViewTrackNumber.text = storeResult.trackNumber.toString()
        }
        else {
            binding.textViewTrackNumber.visibility = View.GONE
            binding.textViewTrackNumberLabel.visibility = View.GONE
        }

        // track time
        storeResult.trackTimeMillis?.apply {
            val totalSeconds = this / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60

            binding.textViewTrackTime.text = getString(R.string.track_time_format, minutes, seconds)
        }

        if (storeResult.trackTimeMillis == null) {
            binding.textViewTrackTimeLabel.visibility = View.GONE
            binding.textViewTrackTime.visibility = View.GONE
        }

        // country
        binding.textViewCountry.text = storeResult.country

        // currency
        binding.textViewCurrency.text = storeResult.currency

        // genre
        binding.textViewGenre.text = storeResult.primaryGenreName
    }

    private fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).setCollapsingToolbarTitle(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}