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
import com.mehmetkaanaydenk.tmdb.adapter.SearchTvAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentSearchTvTabBinding
import com.mehmetkaanaydenk.tmdb.viewmodel.SearchFragmentModel


class SearchTvTabFragment : Fragment() {

    private var _binding: FragmentSearchTvTabBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: SearchFragmentModel

    private lateinit var adapter: SearchTvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[SearchFragmentModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTvTabBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.query.observe(viewLifecycleOwner) {

            viewModel.getTv(it)
            observeData()
        }


    }

    private fun observeData() {

        viewModel.tvSeries.observe(viewLifecycleOwner) {

            adapter = SearchTvAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.tvTabRecyclerView.layoutManager = manager
            binding.tvTabRecyclerView.adapter = adapter
            binding.tvTabRecyclerView.visibility = View.VISIBLE

        }
        viewModel.tvLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvProgressBar.visibility = View.VISIBLE
            } else {
                binding.tvProgressBar.visibility = View.GONE
            }
        }
        viewModel.tvNotFound.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvTabNotFoundText.visibility = View.VISIBLE
                binding.tvTabRecyclerView.visibility = View.GONE
            } else {
                binding.tvTabNotFoundText.visibility = View.GONE
                binding.tvTabRecyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.tvError.observe(viewLifecycleOwner) {

            if (it) {
                binding.tvTabErrorText.visibility = View.VISIBLE
                binding.tvTabRecyclerView.visibility = View.GONE
            } else {
                binding.tvTabErrorText.visibility = View.GONE
                binding.tvTabRecyclerView.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}