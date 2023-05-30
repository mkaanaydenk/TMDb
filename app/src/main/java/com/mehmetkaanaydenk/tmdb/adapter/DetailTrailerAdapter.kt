package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.DetailTrailerRowBinding
import com.mehmetkaanaydenk.tmdb.model.Video
import com.mehmetkaanaydenk.tmdb.model.Videos

class DetailTrailerAdapter(val trailerList: List<Video>):RecyclerView.Adapter<DetailTrailerAdapter.TrailerHolder>() {
    class TrailerHolder(val binding: DetailTrailerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val binding = DetailTrailerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrailerHolder(binding)
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        holder.binding.trailerTextView.text = trailerList[position].name
    }
}