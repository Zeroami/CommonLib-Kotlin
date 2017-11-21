package com.zeroami.app

import io.reactivex.disposables.Disposable


interface IMainModel{

    fun login(account:String,password:String,callback: (e: Throwable?,result:String?) -> Unit) : Disposable
}
