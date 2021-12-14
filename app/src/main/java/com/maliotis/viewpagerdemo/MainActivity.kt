package com.maliotis.viewpagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager2)
        // some viewPager attributes
        viewPager?.apply {
            clipToPadding = false
            clipChildren = false

            val compositeTransformer = CompositePageTransformer()
            compositeTransformer.addTransformer(MarginPageTransformer(40))
            compositeTransformer.addTransformer(ZoomOutPageTransformer())
            setPageTransformer(compositeTransformer)
        }

        // create the different layouts
        val views = createLayouts()

        // create the adapter
        val adapter = PageAdapter(supportFragmentManager, lifecycle, views)

        viewPager.adapter = adapter
    }

    private fun createLayouts(): MutableList<View> {
        val views = mutableListOf<View>()
        val inflater = LayoutInflater.from(this)

        // create layout_1
        val view: ConstraintLayout = inflater.inflate(R.layout.layout_1, null) as ConstraintLayout
        populateImageViews(view)
        views.add(view)

        // create layout_2
        val view2: ConstraintLayout = inflater.inflate(R.layout.layout_2, null) as ConstraintLayout
        populateImageViews(view2)
        views.add(view2)

        // create layout_3
        val view3: ConstraintLayout = inflater.inflate(R.layout.layout_3, null) as ConstraintLayout
        populateImageViews(view3)
        views.add(view3)

        // return array of views
        return views
    }

    private fun populateImageViews(parent: View) {
        parent as ConstraintLayout
        for (view in (parent.children.first() as ConstraintLayout).children) {
            if (view is ShapeableImageView) {
                val shape = view.shapeAppearanceModel.toBuilder()
                    .setAllCornerSizes(10f)
                    .build()
                view.shapeAppearanceModel = shape
                Glide.with(this)
                    .load("https://picsum.photos/200")
                    .into(view)
            }
        }
    }
}