package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.TvFragmentRecyclerRowBinding
import com.mehmetkaanaydenk.tmdb.model.ResultTv
import com.mehmetkaanaydenk.tmdb.model.Tv
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.view.TvseriesFragmentDirections

class TvAdapter(val tvList: List<ResultTv>) : RecyclerView.Adapter<TvAdapter.TvHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154"

    class TvHolder(val binding: TvFragmentRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val binding =
            TvFragmentRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        holder.binding.tvNameText.text = tvList[position].name
        holder.binding.tvReleaseYearText.text = tvList[position].firstAirDate
        holder.binding.tvImageView.downloadUrl(
            IMAGE_BASE_URL + tvList[position].posterPath,
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.binding.tvOverviewText.text = tvList[position].overview

        holder.itemView.setOnClickListener {

            val action = TvseriesFragmentDirections.actionTvseriesFragmentToTvDetailsFragment(tvList[position].id)
            it.findNavController().navigate(action)

        }

    }
}