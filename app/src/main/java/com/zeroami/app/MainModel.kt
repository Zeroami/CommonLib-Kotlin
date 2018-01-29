package com.zeroami.app

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URL


class MainModel : IMainModel {

    override fun login(account: String, password: String, callback: (e: Throwable?, result: String?) -> Unit): Disposable {
        return Observable.create<String> {
            it.onNext(URL("https://www.baidu.com").readText())
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback(null, it) }, { callback(it, null) })
    }
}