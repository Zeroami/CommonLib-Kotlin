package com.zeroami.app

import com.zeroami.commonlib.mvp.LBaseMvpFragment
import com.zeroami.commonlib.mvp.LMvpPresenter

abstract class BaseMvpFragment<out P : LMvpPresenter<*>> : LBaseMvpFragment<P>() {

}
