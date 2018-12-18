package com.aib.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils

import java.util.ArrayList


import com.wm.loan.R
import com.wm.loan.databinding.ActivityGuideBinding

/**
 * 引导页
 */
class GuideActivity : BaseActivity<ActivityGuideBinding>() {
    private val images = intArrayOf(R.drawable.ic_guide1, R.drawable.ic_guide2, R.drawable.ic_guide3)
    private val imageViews = ArrayList<ImageView>()
    private var currentItem: Int = 0

    override fun getResId(): Int = R.layout.activity_guide

    @SuppressLint("ClickableViewAccessibility")
    override fun initData(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.setStatusBarAlpha(this, 0)

        for (i in images.indices) {
            val iv = ImageView(applicationContext)
            iv.setBackgroundResource(images[i])
            imageViews.add(iv)
        }

        binding.vp.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return if (images.size == imageViews.size) imageViews.size else 0
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val iv = imageViews[position]
                container.addView(iv)
                return iv
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }

        binding.vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentItem = position
                if (position == imageViews.size - 1) {
                    binding.btnEnter.visibility = View.VISIBLE
                } else {
                    binding.btnEnter.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.btnEnter.setOnClickListener {
            enterLogin()
        }


        binding.vp.setOnTouchListener(object : View.OnTouchListener {
            var startX: Float = 0.toFloat()
            var startY: Float = 0.toFloat()
            var endX: Float = 0.toFloat()
            var endY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                    }
                    MotionEvent.ACTION_UP -> {
                        endX = event.x
                        endY = event.y
                        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                        //获取屏幕的宽度
                        val size = Point()
                        windowManager.defaultDisplay.getSize(size)
                        val width = size.x
                        if (currentItem == imageViews.size - 1 && startX - endX > 0 && startX - endX >= width / 4) {
                            enterLogin()
                        }
                    }
                }
                return false
            }
        })

    }

    private fun enterLogin() {
        ActivityUtils.startActivity(LoginActivity::class.java)
        overridePendingTransition(R.anim.anim_guide_right, R.anim.anim_guide_left)
        finish()
    }
}
