package com.example.restaurants.ui.forms

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.example.restaurants.data.response.PostReviewResponse
import com.example.restaurants.data.retrofit.ApiConfig
import com.example.restaurants.databinding.ActivityDetailBinding
import com.example.restaurants.databinding.ActivityInsertReviewBinding
import com.example.restaurants.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsertReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Add Review"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.setOnClickListener{
            val id = intent.getStringExtra(RESTAURANT_ID)
            val name = binding.edName.text.toString()
            val review = binding.edReview.text.toString()

            postReview(id!!, name, review)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun postReview(id: String, name: String, review: String) {
        val client = ApiConfig.getApiService().postReview(id, name, review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null)
                {
                    finish()
                }
                else
                {
                    Log.e(TAG, "onFailure: ${response.message()}, $RESTAURANT_ID")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "InsertReviewActivity"
        const val RESTAURANT_ID = "restaurant_id"
    }

}