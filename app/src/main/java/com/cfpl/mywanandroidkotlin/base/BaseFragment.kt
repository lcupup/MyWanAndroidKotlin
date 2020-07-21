package com.cfpl.mywanandroidkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cfpl.mywanandroidkotlin.R
import com.shehuan.statusview.StatusView
import com.shehuan.statusview.StatusViewBuilder

/**
 *Date: 2020/7/20
 *User: LiChao
 */
abstract class BaseFragment  :Fragment(){
//    protected val   TAG  :String =this.javaClass
   lateinit var  mContext : BaseActivity

    private  var  isViewCreated :Boolean =false // 界面是否 创建完成
    protected var isVisibleToUser :Boolean =false //是否 对用户可见
    private var isDataLoaded :Boolean =false //数据是否请求，isNeedReload() 返回 false 时起作用
    private var isFragmentHidden :Boolean =true// 当前 fragment 是否隐藏

    /**
     * 设置布局ID
     */
     abstract fun  setLayoutRedID():Int

    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()

    /**
     * 加载网络数据
     */
    abstract fun initLoadNet()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutRedID(),container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        initData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated=true
        tryLoadData()
    }

    /**
     * 使用viewpager 嵌套 fragment 切换viewpager  回调
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser=isVisibleToUser
        tryLoadData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentHidden=hidden
        if (!isHidden){
            tryLoadData1()
        }
    }

    private  fun  isParentVisible():Boolean{
        val  fragment: Fragment? =parentFragment
        return fragment == null  || (fragment is BaseFragment && fragment.isVisibleToUser)


    }
    private fun  isParentHidden() :Boolean{
        val fragment :Fragment ?=parentFragment
        if (fragment ==null){
            return false
        }else  if ( fragment is BaseFragment && !fragment.isHidden){
            return false
        }

      return true
    }
    protected fun  tryLoadData(){
        if (isViewCreated && isVisibleToUser &&   isParentVisible() &&(isNeedReload() || !isDataLoaded) )
        {
         initLoadNet()
         isDataLoaded =true
        }
    }

    /**
     * show() hide()   场景下 ， 尝试请求数据
     *
     */
    protected  fun tryLoadData1(){
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)){
            initLoadNet()
            isDataLoaded=true
            dispatchParentVisibleState()
        }
    }

    /**
     * fragment 再次可见时 ，是否重新请求数据，默认为false  只请求一次
     */
    protected    fun  isNeedReload ()  :Boolean =false


  private   fun dispatchParentVisibleState (){
      val fragmentManager:FragmentManager =childFragmentManager
      val fragments :List<Fragment>  =fragmentManager.fragments
      if (fragments.isEmpty()){
          return
      }

      for (child in fragments){
          if (child is BaseFragment && child.isVisibleToUser){
              child.tryLoadData()
          }
      }

  }

    protected  lateinit var  statusView : StatusView

    protected fun initStatusView(id : Int, errorRetry :(View) ->Unit){
    initStatusView(StatusView.init(this,id),errorRetry)
    }

    protected  fun initStatusView (statusView: StatusView,errorRetry: (View) -> Unit){
        this.statusView =statusView.apply {
            setLoadingView(R.layout.dialog_loading_layout)
            config(
                StatusViewBuilder.Builder()
                    .showEmptyRetry(false)
                    .setOnErrorRetryClickListener(errorRetry).build())
        }
    }

    override fun onDestroy() {
        isViewCreated=false
        isVisibleToUser =false
        isDataLoaded=false
        isFragmentHidden =true

        super.onDestroy()
    }

}