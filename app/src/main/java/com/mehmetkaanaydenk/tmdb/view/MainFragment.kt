package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.sidesheet.SideSheetBehavior
import com.google.android.material.sidesheet.SideSheetDialog
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.DiscoveryParentAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentMainBinding
import com.mehmetkaanaydenk.tmdb.model.Movie
import com.mehmetkaanaydenk.tmdb.model.ResultMovie
import com.mehmetkaanaydenk.tmdb.viewmodel.DiscoveryViewModel


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: DiscoveryViewModel

    private lateinit var discoveryParentAdapter: DiscoveryParentAdapter

    private lateinit var movList: ArrayList<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DiscoveryViewModel::class.java)
        viewModel.getDataApi()

        observeLiveData()

    }

    fun observeLiveData() {

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            discoveryParentAdapter = DiscoveryParentAdapter(it)
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.extRecyclerView.layoutManager = layoutManager
            binding.extRecyclerView.adapter = discoveryParentAdapter

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}