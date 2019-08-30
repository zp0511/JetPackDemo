package com.demo.jetpack.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

/**
 * @author kuky.
 * @description paging ArticleAdapter 基类
 */
abstract class BasePagerListAdapter<T, V : View>(val callback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, BaseViewHolders<V>>(callback) {

    private var itemListener: OnItemClickListener? = null

    /**
     * 点击监听
     * @param listener
     */
    fun setOnItemListener(listener: OnItemClickListener?) {
        this.itemListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolders<V> {
        return BaseViewHolders(
            LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolders<V>, position: Int) {
        val data = getItem(position) ?: return
        setVariable(data, position, holder)
        holder.itemView.setOnClickListener { v -> itemListener?.onItemClick(position, v) }
    }

    /**
     * 获取对应 position 下的数据
     * @param position
     */
    fun getItemData(position: Int): T? = getItem(position)

    /**
     * 根据 viewType 返回不同布局
     * @param viewType, 通过重写 getItemViewType 支持多布局
     */
    abstract fun getLayoutId(viewType: Int): Int

    /**
     * 与 dataBinding 互相绑定的数据操作
     * @param data 列表中当前 position 的数据
     * @param position 数据的位置
     * @param holder
     */
    abstract fun setVariable(data: T, position: Int, holder: BaseViewHolders<V>)
}
