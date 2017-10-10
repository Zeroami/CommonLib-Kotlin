package com.zeroami.commonlib.extension

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

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

fun Any.delay(delayMillis: Long, block: () -> Unit) {
    handler.postDelayed(block, delayMillis)
}

fun Any.uiThread(block: () -> Unit){
    handler.post(block)
}

fun Any.async(block: () -> Unit){
    Thread(block).start()
}
