package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.widget.Toolbar
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.databinding.FragmentDetailsBinding
import com.mehmetkaanaydenk.tmdb.databinding.FragmentMainBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}