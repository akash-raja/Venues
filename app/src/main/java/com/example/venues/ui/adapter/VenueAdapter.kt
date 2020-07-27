package com.example.venues.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.venues.databinding.VenueItemBinding
import com.example.venues.ui.VenueDataStore

class VenueAdapter(private val listener: (String) -> Unit) :
    ListAdapter<VenueDataStore, VenueViewHolder>(object : DiffUtil.ItemCallback<VenueDataStore>() {

        override fun areItemsTheSame(oldItem: VenueDataStore, newItem: VenueDataStore): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VenueDataStore, newItem: VenueDataStore): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {

        val viewBinding = VenueItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VenueViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venue = getItem(position)
        holder.bindTo(venue)
        holder.itemView.setOnClickListener { listener(venue.id) }
    }
}

class VenueViewHolder(private val viewBinding: VenueItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindTo(venue: VenueDataStore) {
        viewBinding.venueName.text = venue.title
        viewBinding.venueLocation.text = venue.address
    }
}