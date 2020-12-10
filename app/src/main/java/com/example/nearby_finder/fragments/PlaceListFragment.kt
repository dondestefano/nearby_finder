package com.example.nearby_finder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nearby_finder.PlaceAdapter
import com.example.nearby_finder.R
import com.example.nearby_finder.databinding.FragmentPlaceListBinding

class PlaceListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = PlaceAdapter()
        binding.placeList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: PlaceAdapter) {
         //TODO add ViewModel with a list of places
        }
    }

    companion object {
        fun newInstance() = PlaceListFragment()
    }
}