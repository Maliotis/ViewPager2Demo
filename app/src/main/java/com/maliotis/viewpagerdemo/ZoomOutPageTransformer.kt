package com.maliotis.viewpagerdemo

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min

private const val MIN_SCALE = 0.95f
private const val YM = 40.0
private const val Y0 = 0.0
private const val K = 10.0

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    /**
     *  The position is fed to this function with an alternating order between the 2 views showing on the screen
     */
    override fun transformPage(view: View, position: Float) {
        view.apply {
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    val scaleFactor = max(MIN_SCALE, 1 - abs(position))
                    // to always get the same value - this will essentially cap it at 0.5
                    val cap = min(abs(position), abs(1 - position)).toDouble()
                    // Exponential plateau function
                    val radius = expPlateau(YM,Y0, K, cap).toFloat()

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    setCornerRadius(view, radius)
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }


    private fun setCornerRadius(view: View, radius: Float) {
        val drawable = (((view as FrameLayout).children.first() as ConstraintLayout).children.first().background as GradientDrawable)
        drawable.cornerRadius = radius
        (view.children.first() as ConstraintLayout).children.first().background = drawable
    }

    /**
     * y0 is the starting population (same units as y)
     * ym is the maximum population (same units as y)
     * k determines is the rate constant (inverse of x units)
     */
    private fun expPlateau(ym: Double, y0: Double, k: Double, x: Double): Double {
       return ym - (ym - y0) * exp(-k * x)
    }
}