package com.zeroami.app

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL

class MainModel : IMainModel {

    override fun login(account: String, password: String): Observable<String> = Observable.create<String> {
        it.onNext(URL("http://www.baidu.com").readText())
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


}