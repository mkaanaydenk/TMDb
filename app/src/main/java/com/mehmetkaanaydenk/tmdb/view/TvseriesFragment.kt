package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.MovieAdapter
import com.mehmetkaanaydenk.tmdb.adapter.TvAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentMainBinding
import com.mehmetkaanaydenk.tmdb.databinding.FragmentTvseriesBinding
import com.mehmetkaanaydenk.tmdb.model.MovieGenre
import com.mehmetkaanaydenk.tmdb.model.TvGenre
import com.mehmetkaanaydenk.tmdb.viewmodel.MoviesFragmentModel
import com.mehmetkaanaydenk.tmdb.viewmodel.TvFragmentModel


class TvseriesFragment : Fragment() {

    private var _binding: FragmentTvseriesBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: TvFragmentModel

    private lateinit var adapter: TvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[TvFragmentModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvseriesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGenres()
        observeGenres()

        binding.tvChipGroup.setOnCheckedChangeListener { group, checkedId ->

            viewModel.setGenre(group.findViewById<Chip>(checkedId).tag.toString())
            viewModel.getTvs()

        }

        val sortArray = resources.getStringArray(R.array.spinner_array)

        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortArray
        )
        binding.tvSpinner.adapter = arrayAdapter

        binding.tvSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                viewModel.getTvs()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.adultCheckBox.setOnCheckedChangeListener { _, b ->

            viewModel.setAdult(b)
            viewModel.getTvs()

        }

        observeTvs()


    }

    private fun observeTvs() {

        viewModel.tvSeries.observe(viewLifecycleOwner) {

            adapter = TvAdapter(it)
            val manager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.tvRecyclerView.layoutManager = manager
            binding.tvRecyclerView.adapter = adapter

        }
        viewModel.progressBar.observe(viewLifecycleOwner) {
            if (it) {
                binding.movieProgressBar.visibility = View.VISIBLE
            } else {
                binding.movieProgressBar.visibility = View.GONE
            }
        }
        viewModel.tvError.observe(viewLifecycleOwner) {

            if (it) {
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }

        }
        viewModel.recyclerView.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvRecyclerView.visibility = View.VISIBLE
            } else {
                binding.tvRecyclerView.visibility = View.INVISIBLE
            }
        }

    }

    private fun observeGenres() {

        viewModel.tvGenres.observe(viewLifecycleOwner) {

            setChips(it)

        }

    }

    private fun setChips(genreList: List<TvGenre>) {

        genreList.forEach {

            val chip = requireActivity().layoutInflater.inflate(
                R.layout.chip_layout,
                binding.tvChipGroup,
                false
            ) as Chip
            chip.text = it.name
            chip.id = ViewCompat.generateViewId()
            chip.tag = it.id
            binding.tvChipGroup.addView(chip)

        }
        binding.tvChipGroup.isSelectionRequired = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}