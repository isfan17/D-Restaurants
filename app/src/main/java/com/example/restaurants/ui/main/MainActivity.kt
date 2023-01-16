package com.example.restaurants.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.response.ListRestaurantResponse
import com.example.restaurants.data.response.RestaurantsItem
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.ActivityMainBinding
import com.example.restaurants.ui.detail.DetailActivity
import com.example.restaurants.ui.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.layoutManager = layoutManager

        getRestaurants()

        binding.fabSearch.setOnClickListener{
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getRestaurants() {
        showLoading(true)
        val client = ApiConfig.getApiService().getRestaurants()
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
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.RESTAURANT_ID, data.id)
                startActivity(intent)
            }
        })
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
        private const val TAG = "MainActivity"
    }
}