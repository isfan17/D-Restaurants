package com.example.restaurants.ui.detail.menus

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.restaurants.R
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.FragmentMenuBinding
import com.example.restaurants.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenusFragment: Fragment(R.layout.fragment_menu) {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMenuBinding.bind(view)

        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvMenu.layoutManager = layoutManager

        id = arguments?.getString(DetailActivity.RESTAURANT_ID).toString()
        getRestaurantMenus(id)
    }

    private fun getRestaurantMenus(id: String) {
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
                    val responseBody = response.body()
                    if (responseBody != null)
                    {
                        val foods = responseBody.restaurant.menus.foods
                        val drinks = responseBody.restaurant.menus.drinks
                        val adapter = MenuAdapter(foods,drinks)
                        binding.rvMenu.adapter = adapter
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