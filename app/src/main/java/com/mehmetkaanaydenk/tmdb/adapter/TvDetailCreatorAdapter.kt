package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.TvDetailsCreatorsRowBinding
import com.mehmetkaanaydenk.tmdb.model.CreatedBy

class TvDetailCreatorAdapter(val creatorList: List<CreatedBy>):RecyclerView.Adapter<TvDetailCreatorAdapter.CreatorHolder>() {
    class CreatorHolder(val binding: TvDetailsCreatorsRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorHolder {
        val binding = TvDetailsCreatorsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CreatorHolder(binding)
    }

    override fun getItemCount(): Int {
        return creatorList.size
    }

    override fun onBindViewHolder(holder: CreatorHolder, position: Int) {
        holder.binding.creatorNameText.text = creatorList[position].name
    }
}