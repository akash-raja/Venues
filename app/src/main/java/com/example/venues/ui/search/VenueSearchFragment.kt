package com.example.venues.ui.search

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.venues.R
import com.example.venues.databinding.FragmentVenueSearchBinding
import com.example.venues.ui.adapter.VenueAdapter
import com.example.venues.util.Network.isNetworkAvailable
import com.google.android.material.snackbar.Snackbar

class VenueSearchFragment : Fragment() {

    private var _binding: FragmentVenueSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVenueSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val venueSearchViewModel = ViewModelProvider(this,
            VenueSearchViewModelFactory(requireContext().applicationContext as Application)
        ).get(VenueSearchViewModel::class.java)

        val venueAdapter = VenueAdapter {
            val action = VenueSearchFragmentDirections.actionVenueSearchFragmentToVenueDatailsFragment(it)
            if (isNetworkAvailable(requireContext())) {
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please check Internet", Toast.LENGTH_SHORT).show()
            }
        }

        binding.submitButton.setOnClickListener {
            val search = binding.searchVenue.text.toString()
            venueSearchViewModel.search(search, isNetworkAvailable(requireContext()))
        }

        binding.venueRv.adapter = venueAdapter
        venueSearchViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                VenueSearchUIState.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.venueRv.visibility = View.INVISIBLE
                }
                is VenueSearchUIState.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    venueAdapter.submitList(uiState.venueList)
                    binding.venueRv.visibility = View.VISIBLE
                }
                VenueSearchUIState.Failed -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    Snackbar.make(binding.submitButton, R.string.failed_message, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

