package com.example.restaurants.ui.detail.reviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurants.R
import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.FragmentReviewBinding
import com.example.restaurants.ui.detail.DetailActivity
import com.example.restaurants.ui.forms.InsertReviewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsFragment: Fragment(R.layout.fragment_review) {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewBinding.bind(view)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvReview.layoutManager = layoutManager

        id = arguments?.getString(DetailActivity.RESTAURANT_ID).toString()
        getRestaurantReviews(id)

        binding.btnAddReview.setOnClickListener{
            val intent = Intent(requireContext(), InsertReviewActivity::class.java)
            intent.putExtra(InsertReviewActivity.RESTAURANT_ID, id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        id = arguments?.getString(DetailActivity.RESTAURANT_ID).toString()
        getRestaurantReviews(id)
    }

    private fun getRestaurantReviews(id: String) {
        showLoading(true)
        showEmptyMessage(false)

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
                        val adapter = ReviewAdapter(responseBody.restaurant.customerReviews)
                        binding.rvReview.adapter = adapter

                        if (adapter.itemCount == 0) showEmptyMessage(true)
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

}