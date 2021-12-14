package com.maliotis.viewpagerdemo

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fragmentManager: FragmentManager,
                  lifecycle: Lifecycle,
                  val dataSet: List<View>):
    FragmentStateAdapter(fragmentManager, lifecycle) {



    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun createFragment(position: Int): Fragment {
        return PlaceholderFragment(dataSet[position])
    }

}