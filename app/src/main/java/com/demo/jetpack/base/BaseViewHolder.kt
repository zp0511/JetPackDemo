package com.demo.jetpack.base

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @description RecyclerView Adapter View Holder 基类
 */

open class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

open class BaseViewHolders<v : View>(val view: View) : RecyclerView.ViewHolder(view) {
    var views: SparseArray<View> = SparseArray()
    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): BaseViewHolders<v> {
        val view = getView<TextView>(viewId)
        view?.setTextColor(textColor)
        return this
    }

    fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BaseViewHolders<v> {
        val view = getView<ImageView>(viewId)
        view?.setImageResource(imageResId)
        return this
    }

    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolders<v> {
        val view = getView<View>(viewId)
        view?.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BaseViewHolders<v> {
        val view = getView<View>(viewId)
        view?.setBackgroundResource(backgroundRes)
        return this
    }

    fun setText(@IdRes viewId: Int, value: CharSequence): BaseViewHolders<v> {
        val view = getView<TextView>(viewId)
        view?.text = value
        return this
    }

    fun setGone(@IdRes viewId: Int, visible: Boolean): BaseViewHolders<v> {
        val view = getView<View>(viewId)
        view?.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseViewHolders<v> {
        val view = getView<View>(viewId)
        view?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }

    private fun <T : View> getView(@IdRes viewId: Int): T? {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T?
    }
}