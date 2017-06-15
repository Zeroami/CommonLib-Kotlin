package com.zeroami.app

import android.os.Bundle
import com.zeroami.commonlib.rx.rxbus.post
import com.zeroami.commonlib.rx.rxbus.subscribe
import com.zeroami.commonlib.utils.LL
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainContract.Presenter>(),MainContract.View{

    override fun createPresenter(): MainContract.Presenter? = MainPresenter(this)

    override val layoutId: Int = R.layout.activity_main

    override fun initialize(savedInstanceState: Bundle?) {
        subscribe<String> {
            LL.e(it)
        }.let { addDisposable(it) }

        post("hello")
    }

    override fun showResult(result: String) {
        tvText.text = result
    }
}

