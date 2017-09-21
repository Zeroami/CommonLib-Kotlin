package com.zeroami.commonlib.extension

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

fun RecyclerView.addOnItemClickListener(l: (adapter: BaseQuickAdapter<*>?, view: View?, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemClickListener() {
        override fun SimpleOnItemClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemLongClickListener(l: (adapter: BaseQuickAdapter<*>?, view: View?, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemLongClickListener() {
        override fun SimpleOnItemLongClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemChildClickListener(l: (adapter: BaseQuickAdapter<*>?, view: View?, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemChildClickListener() {
        override fun SimpleOnItemChildClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
            l(adapter, view, position)
        }
    })
}

fun RecyclerView.addOnItemChildLongClickListener(l: (adapter: BaseQuickAdapter<*>?, view: View?, position: Int) -> Unit) {
    this.addOnItemTouchListener(object : OnItemChildLongClickListener() {
        override fun SimpleOnItemChildLongClick(adapter: BaseQuickAdapter<*>?, view: View?, position: Int) {
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


var isAdded = false
var move = false
var index = 0

var onScrollListener = object : RecyclerView.OnScrollListener() {
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

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
    }
}

fun RecyclerView.smoothMoveToPosition(position: Int) {
    if (position < 0) return
    if (this.layoutManager is LinearLayoutManager) {
        index = position
        if (!isAdded) {
            this.addOnScrollListener(onScrollListener)
            isAdded = true
        }
        val manager = this.layoutManager as LinearLayoutManager
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        if (position <= firstItem) {
            this.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            val top = this.getChildAt(position - firstItem).top
            this.smoothScrollBy(0, top)
        } else {
            this.smoothScrollToPosition(position)
            move = true
        }
    }
}
