package com.zeroami.commonlib.mvp

/**
 * 空的Contract
 *
 * @author Zeroami
 */
interface LEmptyContract {

    interface View : LMvpView

    interface Presenter : LMvpPresenter<View>
}
