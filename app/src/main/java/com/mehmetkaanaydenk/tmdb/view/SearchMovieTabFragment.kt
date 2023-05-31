package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.SearchMovieAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentSearchMovieTabBinding
import com.mehmetkaanaydenk.tmdb.databinding.FragmentSearchTvTabBinding
import com.mehmetkaanaydenk.tmdb.viewmodel.SearchFragmentModel


class SearchMovieTabFragment : Fragment() {

    private var _binding: FragmentSearchMovieTabBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: SearchFragmentModel

    private lateinit var adapter: SearchMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[SearchFragmentModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMovieTabBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.query.observe(viewLifecycleOwner) {

            viewModel.getMovies(it)

        }
        observeData()
    }

    private fun observeData() {

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter = SearchMovieAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.movieTabRecyclerView.layoutManager = manager
            binding.movieTabRecyclerView.adapter = adapter
            binding.movieTabRecyclerView.visibility = View.VISIBLE
        }

        viewModel.movieLoading.observe(viewLifecycleOwner) {

            if (it) {
                binding.movieProgressBar.visibility = View.VISIBLE
            } else {
                binding.movieProgressBar.visibility = View.GONE
            }

        }

        viewModel.movieNotFound.observe(viewLifecycleOwner) {
            if (it) {
                binding.movieTabNotFoundText.visibility = View.VISIBLE
                binding.movieTabRecyclerView.visibility = View.GONE
            } else {
                binding.movieTabNotFoundText.visibility = View.GONE
                binding.movieTabRecyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.movieError.observe(viewLifecycleOwner) {

            if (it) {
                binding.movieTabErrorText.visibility = View.VISIBLE
                binding.movieTabRecyclerView.visibility = View.GONE
            } else {
                binding.movieTabErrorText.visibility = View.GONE
                binding.movieTabRecyclerView.visibility = View.VISIBLE
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}