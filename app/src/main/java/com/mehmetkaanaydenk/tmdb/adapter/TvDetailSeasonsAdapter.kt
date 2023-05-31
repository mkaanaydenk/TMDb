package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.databinding.TvDetailsSeasonsRowBinding
import com.mehmetkaanaydenk.tmdb.model.Season
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar

class TvDetailSeasonsAdapter(val seasonList: List<Season>) :
    RecyclerView.Adapter<TvDetailSeasonsAdapter.SeasonHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"

    class SeasonHolder(val binding: TvDetailsSeasonsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonHolder {
        val binding =
            TvDetailsSeasonsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonHolder(binding)
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    override fun onBindViewHolder(holder: SeasonHolder, position: Int) {
        holder.binding.seasonImageview.downloadUrl(
            IMAGE_BASE_URL + seasonList[position].posterPath,
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.binding.seasonNameText.text = seasonList[position].name
        val episode = seasonList[position].episodeCount.toString() + holder.itemView.context.getString(
            R.string.episodes)
        holder.binding.episodeCountText.text = episode
        holder.binding.seasonOverviewText.text = seasonList[position].overview
    }


}