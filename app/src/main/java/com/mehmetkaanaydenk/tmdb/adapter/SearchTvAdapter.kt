package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.FragmentSearchTvTabBinding
import com.mehmetkaanaydenk.tmdb.databinding.SearchTvRecyclerRowBinding
import com.mehmetkaanaydenk.tmdb.model.ResultTv
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.view.SearchFragmentDirections

class SearchTvAdapter(val tvList: List<ResultTv>): RecyclerView.Adapter<SearchTvAdapter.TvHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154"

    class TvHolder(val binding: SearchTvRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val binding= SearchTvRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        holder.binding.tvNameText.text = tvList[position].name
        holder.binding.tvReleaseYearText.text = tvList[position].firstAirDate.take(4)
        holder.binding.tvImageView.downloadUrl(IMAGE_BASE_URL+tvList[position].posterPath,
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.binding.tvOverviewText.text = tvList[position].overview

        holder.itemView.setOnClickListener {

            val action = SearchFragmentDirections.actionSearchFragmentToTvDetailsFragment(tvList[position].id)
            it.findNavController().navigate(action)

        }
    }
}