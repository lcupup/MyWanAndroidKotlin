package com.cfpl.mywanandroidkotlin.base

import android.os.Bundle
import com.cfpl.mywanandroidkotlin.mvp.BasePresenter

/**
 *Date: 2020/7/21
 *User: LiChao
 */
abstract class MVPBaseFragment<P :BasePresenter<*>> :BaseFragment(){
   private lateinit var presenter: P

    abstract fun  initPresenter() :P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        presenter =initPresenter()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}