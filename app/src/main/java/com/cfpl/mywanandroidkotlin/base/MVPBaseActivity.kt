package com.cfpl.mywanandroidkotlin.base

import android.os.Bundle
import com.cfpl.mywanandroidkotlin.mvp.BasePresenter

/**
 *Date: 2020/7/20
 *User: LiChao
 */
 abstract  class MVPBaseActivity<P :BasePresenter<*>> :BaseActivity() {

    lateinit var presenter: P

    abstract fun initPresenter() :P

 override fun onCreate(savedInstanceState: Bundle?) {
  presenter =initPresenter()
  super.onCreate(savedInstanceState)
 }

 override fun onDestroy() {
  presenter.detach()
  super.onDestroy()
 }


}