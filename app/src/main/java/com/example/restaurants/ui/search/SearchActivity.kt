package com.example.restaurants.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.response.ListRestaurantResponse
import com.example.restaurants.data.response.RestaurantsItem
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.ActivityMainBinding
import com.example.restaurants.databinding.ActivitySearchBinding
import com.example.restaurants.ui.detail.DetailActivity
import com.example.restaurants.ui.main.MainActivity
import com.example.restaurants.ui.main.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Find Our Restaurants"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.layoutManager = layoutManager

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                val query = binding.etSearch.text.toString()
                searchRestaurants(query)

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            searchRestaurants(query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchRestaurants(query: String) {
        showLoading(true)
        showEmptyMessage(false)

        val client = ApiConfig.getApiService().getSearchRestaurants(query)
        client.enqueue(object: Callback<ListRestaurantResponse> {
            override fun onResponse(
                call: Call<ListRestaurantResponse>,
                response: Response<ListRestaurantResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful)
                {
                    val responseBody = response.body()
                    if (responseBody != null)
                    {
                        if (responseBody.founded == 0)
                        {
                            showEmptyMessage(true)
                        }
                        setRestaurantData(responseBody.restaurants)
                    }
                    else
                    {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ListRestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    private fun setRestaurantData(restaurants: List<RestaurantsItem>) {
        val listRestaurants = ArrayList<RestaurantsItem>()
        for (restaurant in restaurants)
        {
            listRestaurants.add(restaurant)
        }

        val adapter = RestaurantAdapter(listRestaurants)
        binding.rvRestaurants.adapter = adapter
        adapter.setOnItemClickCallback(object: RestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RestaurantsItem) {
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.RESTAURANT_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun showEmptyMessage(isEmpty: Boolean) {
        if (isEmpty)
        {
            binding.apply {
                ivEmpty.visibility = View.VISIBLE
                tvEmptyTitle.visibility = View.VISIBLE
                tvEmptyDesc.visibility = View.VISIBLE
            }
        }
        else
        {
            binding.apply {
                ivEmpty.visibility = View.GONE
                tvEmptyTitle.visibility = View.GONE
                tvEmptyDesc.visibility = View.GONE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
        {
            binding.progressBar.visibility = View.VISIBLE
        }
        else
        {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "SearchActivity"
    }
}