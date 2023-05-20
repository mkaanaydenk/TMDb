package com.mehmetkaanaydenk.tmdb.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayoutMediator
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.adapter.ViewPagerAdapter
import com.mehmetkaanaydenk.tmdb.databinding.FragmentSearchBinding
import com.mehmetkaanaydenk.tmdb.viewmodel.SearchFragmentModel
import java.net.URLEncoder


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: SearchFragmentModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[SearchFragmentModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout()

        binding.searchInputText.setOnEditorActionListener { _, i, _ ->

            if (i==EditorInfo.IME_ACTION_SEARCH){

                val string = binding.searchInputText.text.toString()
                if (string != ""){
                    val strippedString = stripAccents(string)
                    val query = URLEncoder.encode(strippedString,"UTF-8")
                    viewModel.setQuery(query)
                }else{
                    binding.searchInputText.error = getString(R.string.search_null_error)
                }

                true
            }else{
                false
            }

        }

    observeData()

    }

    private fun observeData(){

        viewModel.tabLayout.observe(viewLifecycleOwner){

            if (it){
                binding.searchTabLayout.visibility = View.VISIBLE
                binding.searchViewPager.visibility = View.VISIBLE
            }else{
                binding.searchTabLayout.visibility = View.GONE
                binding.searchViewPager.visibility = View.GONE
            }

        }

    }

    private fun stripAccents(s: String):String {

        val chars: CharArray = s.toCharArray()

        val sb = StringBuilder(s)
        var cont = 0

        while (chars.size > cont) {
            var c: Char = chars[cont]
            var c2: String = c.toString()

            c2 = c2.replace("ö", "o")
            c2 = c2.replace("Õ", "O")
            c2 = c2.replace("Ç", "C")
            c2 = c2.replace("ç", "c")
            c2 = c2.replace("ş", "s")
            c2 = c2.replace("ğ", "g")
            c2 = c2.replace("ı", "i")
            c2 = c2.replace("ü", "u")
            c2 = c2.replace("Ü", "U")
            c2 = c2.replace("İ", "I")

            c = c2.single()
            sb.setCharAt(cont, c)
            cont++

        }

        return sb.toString()
    }

    private fun tabLayout(){

        val tabArray = arrayOf(

            getString(R.string.movies),
            getString(R.string.tv_series)

        )

        val viewPager = binding.searchViewPager
        val tabLayout = binding.searchTabLayout

        val adapter = ViewPagerAdapter(childFragmentManager,lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}