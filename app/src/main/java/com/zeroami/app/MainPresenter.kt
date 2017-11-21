package com.zeroami.app

import com.zeroami.commonlib.mvp.LBasePresenter

class MainPresenter(view: MainContract.View) : LBasePresenter<MainContract.View>(view), MainContract.Presenter {

    private val mainModel by lazy { MainModel() }

    override fun doViewInitialized() {
        mainModel.login("123", "456"){
            e,result ->
            if (e == null){
                mvpView.showResult(result!!)
            }
        }.let { addDisposable(it) }
    }
}
