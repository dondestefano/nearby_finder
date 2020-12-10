package com.example.nearby_finder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class PlaceAdapter(): RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        return RecyclerView.PlaceViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.view_place,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class PlaceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }


}