package com.example.restaurants.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.restaurants.data.response.CustomerReviewsItem
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.response.Restaurant
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val id = intent.getStringExtra(RESTAURANT_ID)

        val bundle = Bundle()
        bundle.putString(RESTAURANT_ID, id)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val TAG = "DetailActivity"
        const val RESTAURANT_ID = "restaurant_id"
    }
}