package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.DetailCastRowBinding
import com.mehmetkaanaydenk.tmdb.model.Cast
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar

class DetailCastAdapter(val castList: List<Cast>):RecyclerView.Adapter<DetailCastAdapter.CastHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185"

    class CastHolder(val binding: DetailCastRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val binding = DetailCastRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastHolder(binding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        holder.binding.castName.text = castList[position].name
        holder.binding.castCharacterName.text = castList[position].character
        holder.binding.castImageview.downloadUrl(IMAGE_BASE_URL+castList[position].profilePath,
            placeHolderProgressBar(holder.itemView.context)
        )

    }
}