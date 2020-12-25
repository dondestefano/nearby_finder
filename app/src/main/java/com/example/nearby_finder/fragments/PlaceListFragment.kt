package com.example.nearby_finder.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.nearby_finder.NearbyFinderApplication
import com.example.nearby_finder.adapters.PlaceAdapter
import com.example.nearby_finder.PlacesViewModel
import com.example.nearby_finder.databinding.FragmentPlaceListBinding
import com.example.nearby_finder.managers.PlaceManager

class PlaceListFragment : Fragment() {

    private val viewModel: PlacesViewModel by viewModels {
        PlacesViewModel.PlacesViewModelFactory((activity?.application as NearbyFinderApplication).repository)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.toolbar.findButton.setOnClickListener {
            this.context?.let { viewModel.findNearby(it) }
        }

        val adapter = PlaceAdapter()
        binding.placeList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun subscribeUi(adapter: PlaceAdapter) {
        viewModel.places.observe(viewLifecycleOwner) { places ->
            places.let { adapter.submitList(places) }

            viewModel.saveToCache()
        }
        this.context?.let { viewModel.isOnline(it) }
    }

    companion object {
        fun newInstance() = PlaceListFragment()
    }
}