package com.example.restaurants.ui.detail.desc

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.restaurants.R
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.FragmentDescriptionBinding
import com.example.restaurants.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionFragment: Fragment(R.layout.fragment_description) {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDescriptionBinding.bind(view)

        id = arguments?.getString(DetailActivity.RESTAURANT_ID).toString()
        getRestaurantDesc(id)
    }

    private fun getRestaurantDesc(id: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getRestaurantDetail(id)
        client.enqueue(object: Callback<DetailRestaurantResponse> {
            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful)
                {
                    val restaurant = response.body()!!.restaurant
                    if (response.body() != null)
                    {
                        Glide.with(this@DescriptionFragment)
                            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                            .into(binding.ivImage)
                        binding.ivImage.clipToOutline = true

                        val rating = restaurant.rating
                        val name = restaurant.name
                        val location = "${restaurant.city}, ${restaurant.address}"
                        val desc = restaurant.description

                        binding.apply {
                            tvRating.text = rating.toString()
                            tvName.text = name
                            tvLocation.text = location
                            tvDescription.text = desc
                        }
                    }
                }
                else
                {
                    Log.e(DetailActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailRestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.e(DetailActivity.TAG, "onFailure: ${t.message}")
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

}