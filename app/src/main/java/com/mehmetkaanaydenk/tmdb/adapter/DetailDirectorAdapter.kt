package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.DetailDirectorRowBinding
import com.mehmetkaanaydenk.tmdb.model.Crew

class DetailDirectorAdapter(val directorList: List<Crew>): RecyclerView.Adapter<DetailDirectorAdapter.DirectorHolder>() {
    class DirectorHolder(val binding: DetailDirectorRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorHolder {
        val binding = DetailDirectorRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DirectorHolder(binding)
    }

    override fun getItemCount(): Int {
        return directorList.size
    }

    override fun onBindViewHolder(holder: DirectorHolder, position: Int) {
        holder.binding.directorNameText.text = directorList[position].name
    }
}