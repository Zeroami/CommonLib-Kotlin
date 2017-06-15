package com.zeroami.commonlib.utils


import android.widget.Toast

import com.zeroami.commonlib.CommonLib

import java.util.Arrays

/**
 * 单例Toast
 *
 * @author Zeroami
 */
object LT {

    private val toast: Toast by lazy { Toast.makeText(CommonLib.ctx, "", Toast.LENGTH_SHORT) }

    @JvmOverloads fun show(any: Any?, duration: Int = Toast.LENGTH_SHORT) {
        val text: String?
        if (any == null) {
            text = "null"
        } else {
            if (any.javaClass.isArray) {
                text = Arrays.deepToString(any as Array<*>)
            } else {
                text = any.toString()
            }
        }
        toast.setText(text)
        toast.duration = duration
        toast.show();
    }
}
