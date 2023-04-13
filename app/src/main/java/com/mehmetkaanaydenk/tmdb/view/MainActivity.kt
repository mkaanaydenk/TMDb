package com.mehmetkaanaydenk.tmdb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mehmetkaanaydenk.tmdb.R
import com.mehmetkaanaydenk.tmdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}