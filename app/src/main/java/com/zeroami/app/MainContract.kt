package com.zeroami.app

import com.zeroami.commonlib.mvp.LMvpPresenter
import com.zeroami.commonlib.mvp.LMvpView

interface MainContract {

    interface View : LMvpView{
        fun showResult(result:String)
    }

    interface Presenter : LMvpPresenter<View>
}