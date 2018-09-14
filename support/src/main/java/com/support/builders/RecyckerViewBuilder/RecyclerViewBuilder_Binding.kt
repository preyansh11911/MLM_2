package com.support.builders.RecyckerViewBuilder

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.BR
import com.support.POJOModel
import com.support.R

fun <T : POJOModel, U : ViewDataBinding> RecyclerView.setUpWithBinding(
        @LayoutRes layoutResId: Int,
        itemList: ArrayList<T>,
        @RecyclerViewLayoutManager.LayoutManager layoutManager: Int,
        @RecyclerViewLinearLayout.Orientation orientation: Int,
        builder: RecyclerViewBuilder_Binding<T, U>.() -> Unit
) = RecyclerViewBuilder_Binding<T, U>(
        this,
        layoutResId,
        itemList,
        layoutManager,
        orientation
).apply(builder)

class RecyclerViewBuilder_Binding<T : POJOModel, U : ViewDataBinding>
/**
 * @param - Recycler View
 * @param - LIst to bind with RecyclerView
 **/
(
        val recyclerView: RecyclerView,
        val itemView: Int,
        val mItems: ArrayList<T>,
        @RecyclerViewLayoutManager.LayoutManager layoutManager: Int,
        @RecyclerViewLinearLayout.Orientation orientation: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 1011
    private val VIEW_TYPE_LOADER = 1021
    //    var itemView: Int = 0
    var isLoading = false
    var hasMore = false
    var contentBindingListener: ((T, U, Int) -> Unit)? = null
    var loadMoreListener: (() -> Unit)? = null
    var spanCount: (() -> Int)? = { 1 }
        set(value) {
            (recyclerView.layoutManager as GridLayoutManager).spanCount = value!!.invoke()
        }
    var isNestedScrollingEnabled: (() -> Boolean) = { false }
        set(value) {
            recyclerView.isNestedScrollingEnabled = value.invoke()
        }

    init {
        setHasStableIds(true)
        if (layoutManager == RecyclerViewLayoutManager.LINEAR)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, orientation, false)
        else if (layoutManager == RecyclerViewLayoutManager.GRID)
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanCount!!.invoke())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = this
    }

    /**
     * This method is must to perform any operation on a view.
     * For Example : ClickListener, Changing value of any view.
     *
     * This method will provide you the itemModel, bindingObject and itemPosition of seletect item from list.
     */
    fun contentBinder(l: (T, U, Int) -> Unit) {
        contentBindingListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<U>(inflater, itemView, parent, false)
            return MyContentViewHolder(binding)
        } else {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.load_more_progressbar, parent, false)
            return LoaderViewHolder(v)
        }
    }

    override fun getItemCount(): Int = mItems.size

    override fun getItemId(position: Int): Long {
        return mItems[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecyclerViewBuilder_Binding<*, *>.MyContentViewHolder) {
            holder.binding.setVariable(BR.vm, mItems[position])
            holder.binding.executePendingBindings()
            contentBindingListener?.invoke(mItems[position], holder.binding as U, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position].isLoader) VIEW_TYPE_LOADER else VIEW_TYPE_ITEM
    }

    inner class MyContentViewHolder(val binding: U) : RecyclerView.ViewHolder(binding.root)
    inner class LoaderViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView)

    fun enableLoadMore(l: () -> Unit) {
        loadMoreListener = l
        hasMore = true

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val totalItemCount = recyclerView?.layoutManager?.itemCount
                val lastVisibleItem = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val visibleThreshold = 5
                if ((hasMore && !isLoading) && totalItemCount!! <= (lastVisibleItem + visibleThreshold)) {
                    isLoading = true
                    if (loadMoreListener != null) {
                        loadMoreListener?.invoke()
                    }
                }
            }
        })
    }

    fun showLoader() {
        val pModel = POJOModel().apply { isLoader = true } as T
        mItems.add(pModel)
        notifyItemInserted(itemCount - 1)
    }

    fun stopLoader() {
        isLoading = false
    }

    fun removeLastItem() {
        mItems.removeAt(itemCount - 1)
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<T>) {
        val position = itemCount
        mItems.addAll(position, list)
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        mItems.remove(item)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}