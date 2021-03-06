package com.zeroami.commonlib.extension

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

fun RecyclerView.addOnItemClickListener(l: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemClickListener() {
        override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemLongClickListener(l: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemLongClickListener() {
        override fun onSimpleItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemChildClickListener(l: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemChildClickListener() {
        override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemChildLongClickListener(l: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemChildLongClickListener() {
        override fun onSimpleItemChildLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            l(adapter, view, position)
        }
    })
}


fun RecyclerView.moveToPosition(position: Int, offset: Int = 0) {
    if (this.layoutManager is LinearLayoutManager) {
        val manager = this.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, offset)
    }
}


private var move = false
private var index = 0

private var onScrollListener = object : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
            move = false
            val n = index - (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (0 <= n && n < recyclerView.childCount) {
                val top = recyclerView.getChildAt(n).top
                recyclerView.smoothScrollBy(0, top)
            }

        }
    }
}

fun RecyclerView.smoothMoveToPosition(position: Int) {
    if (position < 0) return
    if (this.layoutManager is LinearLayoutManager) {
        index = position
        this.removeOnScrollListener(onScrollListener)
        this.addOnScrollListener(onScrollListener)
        val manager = this.layoutManager as LinearLayoutManager
        val firstPosition = manager.findFirstVisibleItemPosition()
        val lastPosition = manager.findLastVisibleItemPosition()
        if (position - firstPosition > 10) {
            moveToPosition(position - 10)
        }
        if (position <= firstPosition) {
            this.smoothScrollToPosition(position)
        } else if (position <= lastPosition) {
            this.smoothScrollToPosition(lastPosition)
            move = true
        } else {
            this.smoothScrollToPosition(position)
            move = true
        }
    }
}
