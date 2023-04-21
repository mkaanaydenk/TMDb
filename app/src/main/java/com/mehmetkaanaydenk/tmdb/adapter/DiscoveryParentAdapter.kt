package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.RecyclerCardParentBinding
import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.ResultMovie

class DiscoveryParentAdapter(val parentList: List<Movie>) :
    RecyclerView.Adapter<DiscoveryParentAdapter.ParentHolder>() {

    class ParentHolder(val binding: RecyclerCardParentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
        val parentBinding =
            RecyclerCardParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ParentHolder(parentBinding)
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {


        holder.binding.recyclerSingleLine.adapter =
            CardAdapter(parentList.get(position).resultMovies)
        holder.binding.recyclerSingleLine.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.recyclerSingleLine.setHasFixedSize(true)
        holder.binding.parentTitle.text = parentList.get(position).titleName


    }

}