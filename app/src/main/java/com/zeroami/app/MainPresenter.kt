package com.zeroami.app

import com.zeroami.commonlib.mvp.LBasePresenter
import com.zeroami.commonlib.utils.LL

class MainPresenter(view: MainContract.View) : LBasePresenter<MainContract.View, IMainModel>(view), MainContract.Presenter {

    override fun createRealModel(): IMainModel? = MainModel()

    override fun doViewInitialized() {
        val disposable = mvpModel!!.login("123", "456")
                .subscribe({
                    mvpView!!.showResult(it)
                },{
                    LL.e(it)
                })
        addDisposable(disposable)
    }
}
