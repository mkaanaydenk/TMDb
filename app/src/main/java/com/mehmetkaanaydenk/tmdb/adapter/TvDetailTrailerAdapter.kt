package com.mehmetkaanaydenk.tmdb.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore.Video
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.DetailTrailerRowBinding

class TvDetailTrailerAdapter(val trailerList: List<com.mehmetkaanaydenk.tmdb.model.Video>): RecyclerView.Adapter<TvDetailTrailerAdapter.TrailerHolder>() {
    class TrailerHolder(val binding: DetailTrailerRowBinding): RecyclerView.ViewHolder(binding.root) {

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

        holder.itemView.setOnClickListener {

            val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+trailerList[position].key))
            val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerList[position].key))

            try {

                holder.itemView.context.startActivity(intentApp)

            }catch (ex: ActivityNotFoundException){

                holder.itemView.context.startActivity(intentBrowser)

            }

        }

    }
}