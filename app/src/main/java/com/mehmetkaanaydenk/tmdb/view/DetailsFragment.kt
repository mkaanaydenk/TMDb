package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.DetailCastAdapter
import com.mehmetkaanaydenk.tmdb.adapter.DetailDirectorAdapter
import com.mehmetkaanaydenk.tmdb.adapter.DetailProducerAdapter
import com.mehmetkaanaydenk.tmdb.adapter.DetailTrailerAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentDetailsBinding
import com.mehmetkaanaydenk.tmdb.databinding.FragmentMainBinding
import com.mehmetkaanaydenk.tmdb.service.MovieAPIService
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.viewmodel.MovieDetailFragmentModel


class DetailsFragment : Fragment() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailFragmentModel


    private lateinit var directorAdapter: DetailDirectorAdapter

    private lateinit var producerAdapter: DetailProducerAdapter

    private lateinit var castAdapter: DetailCastAdapter

    private lateinit var trailerAdapter: DetailTrailerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[MovieDetailFragmentModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.topAppBar)

        toolbar.setNavigationOnClickListener {

            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
        val args = arguments?.getInt("movieId")

        if (args != null) {
            viewModel.setMovieId(args)
            viewModel.getMovieDetails()
            observeLiveData()
        }

    }

    private fun observeLiveData() {

        viewModel.movieDetail.observe(viewLifecycleOwner) {

            binding.nameText.text = it.title
            binding.imageView.downloadUrl(
                IMAGE_BASE_URL + it.backdropPath,
                placeHolderProgressBar(requireContext())
            )
            binding.genreText.text = it.genres[0].name
            binding.yearText.text = it.releaseDate.take(4)
            val duration = it.runtime.toString() + getString(R.string.minute)
            binding.durationText.text = duration
            binding.overviewText.text = it.overview
            binding.ratingBar.numStars = it.voteAverage.toInt()
            binding.ratingBar.rating = it.voteAverage.toFloat()
            val voteText = it.voteAverage.toString() + "/10"
            binding.voteText.text = voteText

        }

        viewModel.directorList.observe(viewLifecycleOwner) {
            directorAdapter = DetailDirectorAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.directorRecyclerView.layoutManager = manager
            binding.directorRecyclerView.adapter = directorAdapter

        }

        viewModel.producerList.observe(viewLifecycleOwner) {

            producerAdapter = DetailProducerAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.producerRecyclerView.layoutManager = manager
            binding.producerRecyclerView.adapter = producerAdapter

        }

        viewModel.castList.observe(viewLifecycleOwner) {

            castAdapter = DetailCastAdapter(it)
            val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.castRecyclerView.layoutManager = manager
            binding.castRecyclerView.adapter = castAdapter

        }

        viewModel.trailerList.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                binding.trailerText.visibility = View.VISIBLE
                binding.trailerRecyclerView.visibility = View.VISIBLE
                trailerAdapter = DetailTrailerAdapter(it)
                val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
                binding.trailerRecyclerView.layoutManager = manager
                binding.trailerRecyclerView.adapter = trailerAdapter
            } else {
                binding.trailerText.visibility = View.GONE
                binding.trailerRecyclerView.visibility = View.GONE
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}