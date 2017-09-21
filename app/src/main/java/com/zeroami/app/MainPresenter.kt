package com.zeroami.app

import com.zeroami.commonlib.mvp.LBasePresenter
import com.zeroami.commonlib.utils.LL
import java.io.File

class MainPresenter(view: MainContract.View) : LBasePresenter<MainContract.View>(view), MainContract.Presenter {

    private val mainModel : IMainModel by lazy { MainModel() }

    override fun doViewInitialized() {
        val disposable = mainModel.login("123", "456")
                .subscribe({
                    mvpView.showResult(it)
                },{
                    LL.e(it)
                })
        addDisposable(disposable)
    }
}
