package com.scripps.codingchallenge

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scripps.codingchallenge.adapter.StoreResultsRecyclerViewAdapter
import com.scripps.codingchallenge.databinding.FragmentStoreResultsBinding

class StoreResultsFragment : Fragment() {

    private var _binding: FragmentStoreResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerViewSearchResults) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            adapter = StoreResultsRecyclerViewAdapter(requireActivity())
        }

        binding.recyclerViewSearchResults.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (binding.recyclerViewSearchResults.computeVerticalScrollOffset() > 0) {
                    // https://stackoverflow.com/questions/41793069/hide-keyboard-when-edit-text-in-recycler-view-is-scrolled-off-screen
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).setCollapsingToolbarTitle(getString(R.string.store_results_fragment_title))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}