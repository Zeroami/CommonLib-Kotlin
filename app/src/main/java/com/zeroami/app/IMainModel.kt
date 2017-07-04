package com.zeroami.app

import com.zeroami.commonlib.mvp.LMvpModel
import io.reactivex.Observable
import java.io.File


interface IMainModel : LMvpModel{

    fun login(account:String,password:String) : Observable<String>
}
