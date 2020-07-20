package com.cfpl.mywanandroidkotlin.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 *Date: 2020/7/20
 *User: LiChao
 */
 abstract  class BaseActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initView()
        initEvent()
        initData()
    }
    /**
     * 设置布局 id
     */
    @LayoutRes
    abstract fun setLayoutId ():Int

    /**
     * 初始化 view
     *
     */
    abstract fun initView()

    /**
     *初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 事件
     */
    abstract fun initEvent()





}