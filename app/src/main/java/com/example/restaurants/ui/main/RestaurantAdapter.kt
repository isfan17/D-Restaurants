package com.example.restaurants.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurants.data.response.RestaurantsItem
import com.example.restaurants.databinding.ItemRestaurantBinding

class RestaurantAdapter(private val listRestaurant: List<RestaurantsItem>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(listRestaurant[position])
    }

    override fun getItemCount(): Int = listRestaurant.size

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: RestaurantsItem) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(restaurant)
                }
                tvName.text = restaurant.name
                tvLocation.text = restaurant.city
                tvRating.text = restaurant.rating.toString()
                Glide.with(itemView)
                    .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                    .centerCrop()
                    .into(ivImage)
                ivImage.clipToOutline = true
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: RestaurantsItem)
    }
}