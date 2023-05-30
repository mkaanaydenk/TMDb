package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.RecyclerCardBinding
import com.mehmetkaanaydenk.tmdb.model.ResultMovie
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.view.MainFragmentDirections

class CardAdapter(val movieList: List<ResultMovie>) :
    RecyclerView.Adapter<CardAdapter.CardHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154"

    class CardHolder(val binding: RecyclerCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {

        val cardBinding =
            RecyclerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardHolder(cardBinding)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.binding.cardMovieTitle.text = movieList.get(position).title
        holder.binding.cardReleaseDate.text = movieList.get(position).releaseDate.take(4)

        holder.binding.cardPosterImageview.downloadUrl(
            IMAGE_BASE_URL + movieList[position].posterPath,
            placeHolderProgressBar(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movieList[position].id)
            it.findNavController().navigate(action)

        }

    }
}