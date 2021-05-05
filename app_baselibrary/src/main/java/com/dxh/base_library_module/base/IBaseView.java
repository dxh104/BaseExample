package com.dxh.base_library_module.base;

/**
 * Created by XHD on 2020/11/16
 */
public interface IBaseView {
    void showLoadingDialog();//显示加载框

    void hideLoadingDialog(); //隐藏加载框

    void requestOnError(Throwable e);//通用请求失败
}
