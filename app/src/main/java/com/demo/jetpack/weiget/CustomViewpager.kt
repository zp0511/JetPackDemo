package com.demo.jetpack.weiget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class CustomViewpager : ViewPager {

    private var isCanScroll = true

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    fun setCanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll) {
            super.onTouchEvent(arg0)
        } else {
            false
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }

    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}