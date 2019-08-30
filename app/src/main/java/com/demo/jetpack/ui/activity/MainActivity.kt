package com.demo.jetpack.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.demo.jetpack.R
import com.demo.jetpack.base.TopBaseActivity
import com.demo.jetpack.ui.fragment.LikeFragment
import com.demo.jetpack.ui.fragment.MyFragment
import com.demo.jetpack.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TopBaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main -> {
                viewPager.currentItem = 0
                return true
            }
            R.id.like -> {
                viewPager.currentItem = 1
                return true
            }
            R.id.my -> {
                viewPager.currentItem = 2
                return true
            }
        }
        return false
    }

    private var fragments: ArrayList<Fragment> = arrayListOf(HomeFragment(), LikeFragment(), MyFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        navigationView.setOnNavigationItemSelectedListener(this)
        viewPager.addOnPageChangeListener(this)
        viewPager.setCanScroll(false)
        navigationView.selectedItemId = R.id.main
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            supportFragmentManager.beginTransaction().show(fragment).commit()
            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
            // 获取要销毁的fragment
            val fragment = fragments[position]
            // 将其隐藏即可，并不需要真正销毁，这样fragment状态就得到了保存
            supportFragmentManager.beginTransaction().hide(fragment).commit()
        }
    }
}
