package com.cfpl.mywanandroidkotlin.mvp

import android.content.Context
import com.cfpl.mywanandroidkotlin.base.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *Date: 2020/7/20
 *User: LiChao
 */
abstract class BasePresenter <out V :BaseView> (val  view: V) {
    protected val context :Context =if (view is BaseFragment  )
    {
      view.mContext
    }else {
        view as Context
    }


    private val compositeDisposable :io.reactivex.disposables.CompositeDisposable =
        CompositeDisposable()

    fun  addDisposable(disposable: Disposable){
        if (!compositeDisposable.isDisposed){
            compositeDisposable.add(disposable)
        }
    }

    fun  removeDisposable(disposable: Disposable){
        if (!compositeDisposable.isDisposed){
            compositeDisposable.remove(disposable)
        }
    }
    fun   detach(){
        if (!compositeDisposable.isDisposed){
            compositeDisposable.clear()
        }
    }

}