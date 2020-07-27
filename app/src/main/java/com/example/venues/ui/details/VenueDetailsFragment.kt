package com.example.venues.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.venues.R
import com.example.venues.databinding.FragmentVenueDetailsBinding
import kotlinx.android.synthetic.main.fragment_venue_details.*

class VenueDetailsFragment : Fragment() {

    private val args: VenueDetailsFragmentArgs by navArgs()

    private var _binding: FragmentVenueDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentVenueDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this, VenueDetailsViewModelFactory(args.venueId))
            .get(VenueDetailsViewModel::class.java)

        viewModel.detailUIData.observe(viewLifecycleOwner, Observer {
            when (it) {
                VenueDetailsUIState.Loading -> progress.visibility = View.VISIBLE

                is VenueDetailsUIState.Success -> {
                    progress.visibility = View.GONE
                    binding.title.text = it.data.title
                    binding.description.text = it.data.description
                    binding.rating.text = it.data.rating
                    binding.address.text = it.data.address
                    binding.contact.text = it.data.contact

                    Glide.with(this).load(it.data.photoUrl).error(R.drawable.fallback).into(binding.photo)
                }
                VenueDetailsUIState.Failed -> {
                    progress.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
