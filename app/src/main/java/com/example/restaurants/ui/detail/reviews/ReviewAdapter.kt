package com.example.restaurants.ui.detail.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.data.response.CustomerReviewsItem

class ReviewAdapter(private val listReview: List<CustomerReviewsItem>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.tvReview.text = listReview[position].review
        holder.tvUser.text = "${listReview[position].name}, ${listReview[position].date}"
    }

    override fun getItemCount(): Int = listReview.size

    inner class ReviewViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvReview: TextView = view.findViewById(R.id.tvReview)
        val tvUser: TextView = view.findViewById(R.id.tvUser)
    }
}