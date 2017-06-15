package com.zeroami.app

import android.app.Application
import com.zeroami.commonlib.CommonLib

class App : Application(){

    override fun onCreate() {
        CommonLib.initialize(applicationContext)
        CommonLib.isDebug = BuildConfig.DEBUG
    }
}

