package com.want.want.fragment.me.viewmodel

import android.app.Application
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.rv.QuickMultiAdapter
import com.want.common.utils.getResDrawable
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.want.AppApplication
import com.want.want.R
import com.want.want.bean.CoinUserInfoBean
import com.want.want.common.CollectContentPage
import com.want.want.common.EditDialogEvent
import com.want.want.common.EditPage
import com.want.want.fragment.me.MeRepository
import com.want.want.rv.RecyclerViewVM
import com.want.want.utils.GlobalSingle

class MeViewModel(app:Application) : BaseRepositoryViewModel<MeRepository>(app, MeRepository()) {

    private var mCoinUserInfoBean: CoinUserInfoBean.Data? = null

    private val mData = arrayListOf<BaseMultiItemViewModel>()
    private val mAdapter = QuickMultiAdapter(mData).apply {
        addType(R.layout.item_rv_me_header,ItemType.ITEM_ME_HEADER)
        addType(R.layout.item_rv_me_item,ItemType.ITEM_ME_ITEM)
    }

    var rvVM = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mAdapter)
    }

    var mRankVM = MeItemViewModel(getApplication()).apply{
        mContent.set("我的积分")
        mIcon.set(R.mipmap.jifen_ico.getResDrawable())
        onClick={
            //todo 跳转积分页
        }
        mShowDivider.set(false)
        mShowMargin.set(true)
    }

    override fun onModelBind() {
        super.onModelBind()
        mData.add(MeHeaderViewModel(getApplication()))

        mData.add(mRankVM)

        mData.add(MeItemViewModel(getApplication()).apply{
            mContent.set("收藏文章")
            mIcon.set(R.mipmap.sc_red_sroke_ico.getResDrawable())
            onClick={
                if (AppApplication.isLogin){
                    GlobalSingle.showEditDialog.value =
                        EditDialogEvent(page = EditPage.COLLECT_ARTICLE,
                            collectContentPage = CollectContentPage.COLLECT_ARTICLE)
                }else {
                    "请先登录".showToast()
                }
             }
            mShowDivider.set(true)
            mShowMargin.set(false)
        })

        mData.add(MeItemViewModel(getApplication()).apply{
            mContent.set("收藏网站")
            mIcon.set(R.mipmap.wangzhan_ico.getResDrawable())
            onClick={
                if (AppApplication.isLogin){
                    GlobalSingle.showEditDialog.value =
                        EditDialogEvent(page = EditPage.WEBSITE,
                            collectContentPage = CollectContentPage.COLLECT_WEBSITE)
                }else {
                    "请先登录".showToast()
                }
             }
            mShowDivider.set(true)
            mShowMargin.set(false)
        })

        mData.add(MeItemViewModel(getApplication()).apply{
            mContent.set("分享文章")
            mIcon.set(R.mipmap.wenzhang_ico.getResDrawable())
            onClick={
                if (AppApplication.isLogin){
                    GlobalSingle.showEditDialog.value =
                        EditDialogEvent(page = EditPage.SHARE_ARTICLE,
                            collectContentPage = CollectContentPage.SHARE_ARTICLE)
                }else {
                    "请先登录".showToast()
                }
             }
            mShowDivider.set(false)
            mShowMargin.set(true)
        })

        mData.add(MeItemViewModel(getApplication()).apply{
            mContent.set("关于我")
            mIcon.set(R.mipmap.xiaolianchenggong_ico.getResDrawable())
            onClick={
                //TODO 跳转关于我的页面
             }
            mShowDivider.set(false)
            mShowMargin.set(true)
        })

        mData.add(MeItemViewModel(getApplication()).apply{
            mContent.set("设置")
            mIcon.set(R.mipmap.shezhi_ico.getResDrawable())
            onClick={
                //TODO 跳转设置页面
             }
            mShowDivider.set(false)
        })
        mAdapter.notifyDataSetChanged()

        requestServer()
    }

    private fun requestServer() {
        if (!AppApplication.isLogin){
            "请先登录".showToast()
            resetLoginState()
            return
        }
        coinUserInfo()
    }

    fun coinUserInfo() {
        launch {
            mRepo.coinUserInfo()?.apply {
                mCoinUserInfoBean = this
                (mData[0] as? MeHeaderViewModel)?.apply {
                    loadAvatar()
                    mUserName.set(mNikeName)
                    mIdAndRank.set("id : $userId  排名 : $rank")
                    mAdapter.notifyItemChanged(0)
                }
                mRankVM.mCoinCount.set("当前积分: $coinCount")
            }
        }
    }

    fun resetLoginState() {
        if (mData.isEmpty())return
        (mData[0] as? MeHeaderViewModel)?.apply {
            mPath.set(UN_LOGIN_PATH)
            mUserName.set("请登录")
            mIdAndRank.set("id : -- 排名 : --")
        }
        mRankVM.mCoinCount.set("当前积分: --")
    }

}