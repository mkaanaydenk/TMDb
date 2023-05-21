package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.MovieAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentMoviesBinding
import com.mehmetkaanaydenk.tmdb.model.MovieGenre
import com.mehmetkaanaydenk.tmdb.viewmodel.MoviesFragmentModel


class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesFragmentModel

    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[MoviesFragmentModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGenres()
        observeGenres()

        binding.movieChipGroup.setOnCheckedChangeListener { group, checkedId ->

            viewModel.setGenre(group.findViewById<Chip>(checkedId).tag.toString())

        }

        val sortArray = resources.getStringArray(R.array.spinner_array)

        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortArray
        )
        binding.movieSpinner.adapter = arrayAdapter

        binding.movieSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when (p2) {

                    0 -> viewModel.setSort("popularity.desc")
                    1 -> viewModel.setSort("popularity.asc")
                    2 -> viewModel.setSort("revenue.desc")
                    3 -> viewModel.setSort("primary_release_date.desc")
                    4 -> viewModel.setSort("primary_release_date.asc")
                    5 -> viewModel.setSort("vote_average.desc")
                    6 -> viewModel.setSort("vote_average.asc")
                    7 -> viewModel.setSort("vote_count.desc")

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.adultCheckBox.setOnCheckedChangeListener { _, b ->

            viewModel.setAdult(b)

        }
        viewModel.includeAdult.observe(viewLifecycleOwner) { includeAdult ->

            viewModel.selectedSortBy.observe(viewLifecycleOwner) { sortBy ->

                viewModel.selectedGenreId.observe(viewLifecycleOwner) {

                    viewModel.getMovies(it, sortBy, includeAdult)
                    observeMovies()
                }
            }

        }


    }


    fun observeMovies() {

        viewModel.movies.observe(viewLifecycleOwner) {

            adapter = MovieAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.movieRecyclerView.layoutManager = manager
            binding.movieRecyclerView.adapter = adapter

        }
        viewModel.progressBar.observe(viewLifecycleOwner) {
            if (it) {
                binding.movieProgressBar.visibility = View.VISIBLE
            } else {
                binding.movieProgressBar.visibility = View.GONE
            }
        }
        viewModel.movieError.observe(viewLifecycleOwner) {

            if (it) {
                binding.movieError.visibility = View.VISIBLE
            } else {
                binding.movieError.visibility = View.GONE
            }

        }
        viewModel.recyclerView.observe(viewLifecycleOwner) {
            if (it) {
                binding.movieRecyclerView.visibility = View.VISIBLE
            } else {
                binding.movieRecyclerView.visibility = View.INVISIBLE
            }
        }


    }

    private fun observeGenres() {

        viewModel.movieGenres.observe(viewLifecycleOwner) {

            setChips(it)

        }

    }

    private fun setChips(genreList: List<MovieGenre>) {

        genreList.forEach {

            val chip = requireActivity().layoutInflater.inflate(
                R.layout.chip_layout,
                binding.movieChipGroup,
                false
            ) as Chip
            chip.text = it.name
            chip.id = ViewCompat.generateViewId()
            chip.tag = it.id
            binding.movieChipGroup.addView(chip)

        }
        binding.movieChipGroup.isSelectionRequired = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}