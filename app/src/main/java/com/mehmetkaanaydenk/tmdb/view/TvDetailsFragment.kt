package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.TvDetailCastAdapter
import com.mehmetkaanaydenk.tmdb.adapter.TvDetailCreatorAdapter
import com.mehmetkaanaydenk.tmdb.adapter.TvDetailSeasonsAdapter
import com.mehmetkaanaydenk.tmdb.adapter.TvDetailTrailerAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentTvDetailsBinding
import com.mehmetkaanaydenk.tmdb.util.downloadUrl
import com.mehmetkaanaydenk.tmdb.util.placeHolderProgressBar
import com.mehmetkaanaydenk.tmdb.viewmodel.TvDetailFragmentModel


class TvDetailsFragment : Fragment() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"
    private val IMAGE_BASE_URL_W300 = "https://image.tmdb.org/t/p/w300"

    private var _binding: FragmentTvDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: TvDetailFragmentModel

    private lateinit var castAdapter: TvDetailCastAdapter

    private lateinit var creatorAdapter: TvDetailCreatorAdapter

    private lateinit var seasonsAdapter: TvDetailSeasonsAdapter

    private lateinit var trailerAdapter: TvDetailTrailerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[TvDetailFragmentModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.topAppBar)

        toolbar.setNavigationOnClickListener {

            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

        val args = arguments?.getInt("tvId")

        if (args != null) {

            viewModel.setTvId(args)
            viewModel.getTvDetail()
            observeLiveData()

        }
    }

    private fun observeLiveData() {

        viewModel.tvDetail.observe(viewLifecycleOwner) {

            binding.nameText.text = it.name
            binding.imageView.downloadUrl(
                IMAGE_BASE_URL + it.backdropPath,
                placeHolderProgressBar(requireContext())
            )
            binding.genreText.text = it.genres[0].name
            binding.firstAirYear.text = it.firstAirDate.take(4)

            val season = it.numberOfSeasons.toString() + getString(R.string.season)
            binding.totalSeason.text = season
            binding.overviewText.text = it.overview
            binding.ratingBar.numStars = it.voteAverage.toInt()
            binding.ratingBar.rating = it.voteAverage.toFloat()
            val vote = it.voteAverage.toString() + "/10"
            binding.voteText.text = vote

            creatorAdapter = TvDetailCreatorAdapter(it.createdBy)
            val creatorManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
            binding.creatorRecyclerView.layoutManager = creatorManager
            binding.creatorRecyclerView.adapter = creatorAdapter

        }

        viewModel.seasonList.observe(viewLifecycleOwner) {

            seasonsAdapter = TvDetailSeasonsAdapter(it)
            val seasonsManager: RecyclerView.LayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.seasonsRecyclerView.layoutManager = seasonsManager
            binding.seasonsRecyclerView.adapter = seasonsAdapter

        }

        viewModel.castList.observe(viewLifecycleOwner) {

            castAdapter = TvDetailCastAdapter(it)
            val castManager: RecyclerView.LayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.castRecyclerView.layoutManager = castManager
            binding.castRecyclerView.adapter = castAdapter
        }

        viewModel.trailerList.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                trailerAdapter = TvDetailTrailerAdapter(it)
                val trailerManager: RecyclerView.LayoutManager =
                    LinearLayoutManager(requireContext())
                binding.trailerRecyclerView.layoutManager = trailerManager
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