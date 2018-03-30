package com.zeroami.commonlib.extension

import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.forEachChild

fun TextView.addTextChangedListener(l: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            l(p0!!.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    })
}

private var handler = Handler(Looper.getMainLooper())

fun delay(delayMillis: Long, block: () -> Unit) {
    handler.postDelayed(block, delayMillis)
}

fun runOnHandler(block: () -> Unit) {
    handler.post(block)
}

fun uiThread(block: () -> Unit) {
    handler.post(block)
}

fun async(block: () -> Unit) {
    Thread(block).start()
}

fun runFragment(activity: FragmentActivity, fragment: Fragment) {
    activity.supportFragmentManager.findFragmentByTag(fragment::javaClass.name)?.let {
        activity.supportFragmentManager.beginTransaction().remove(it).commit()
    }
    activity.supportFragmentManager
            .beginTransaction()
            .add(fragment, fragment::javaClass.name)
            .commit()
    activity.supportFragmentManager.executePendingTransactions()
}

fun ViewGroup.listChilds(): List<View> {
    val list = ArrayList<View>()
    this.forEachChild {
        list.add(it)
    }
    return list
}

fun <T> Collection<T>?.isNotNullAndEmpty(): Boolean = this != null && this.isNotEmpty()