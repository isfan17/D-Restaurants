package com.example.restaurants.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.restaurants.R
import com.example.restaurants.ui.detail.desc.DescriptionFragment
import com.example.restaurants.ui.detail.menus.MenusFragment
import com.example.restaurants.ui.detail.reviews.ReviewsFragment

class SectionPagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2, R.string.tab_3)

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position)
        {
            0 -> fragment = DescriptionFragment()
            1 -> fragment = MenusFragment()
            2 -> fragment = ReviewsFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mCtx.resources.getString(TAB_TITLES[position])
    }
}