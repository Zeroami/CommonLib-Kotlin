package com.zeroami.app

import com.zeroami.commonlib.mvp.LBaseMvpActivity
import com.zeroami.commonlib.mvp.LMvpPresenter

abstract class BaseMvpActivity<out P : LMvpPresenter<*>> : LBaseMvpActivity<P>() {

}
