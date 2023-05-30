package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.MovieFragmentRecyclerRowBinding
import com.mehmetkaanaydenk.tmdb.model.ResultMovie
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.view.MoviesFragmentDirections

class MovieAdapter(val movieList: List<ResultMovie>):RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w154"

    class MovieHolder(val binding: MovieFragmentRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieFragmentRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.binding.movieNameText.text = movieList[position].title
        holder.binding.movieReleaseYearText.text = movieList[position].releaseDate.take(4)
        holder.binding.movieImageView.downloadUrl(IMAGE_BASE_URL+movieList[position].posterPath,
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.binding.movieOverviewText.text = movieList[position].overview

        holder.itemView.setOnClickListener {

            val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieList[position].id)
            it.findNavController().navigate(action)

        }

    }
}