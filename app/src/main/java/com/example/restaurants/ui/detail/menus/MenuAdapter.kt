package com.example.restaurants.ui.detail.menus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.data.response.DrinksItem
import com.example.restaurants.data.response.FoodsItem

class MenuAdapter(private val listFood: List<FoodsItem>, private val listDrink: List<DrinksItem>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var listMenu: List<Any> = listFood + listDrink

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        when (val item = listMenu[position])
        {
            is FoodsItem -> {
                holder.tvMenu.text = item.name
                holder.ivIcon.setImageResource(R.drawable.ic_food)
            }
            is DrinksItem -> {
                holder.tvMenu.text = item.name
                holder.ivIcon.setImageResource(R.drawable.ic_drink)
            }
        }
    }

    override fun getItemCount(): Int = listFood.size + listDrink.size

    inner class MenuViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvMenu: TextView = view.findViewById(R.id.tvMenu)
    }

}