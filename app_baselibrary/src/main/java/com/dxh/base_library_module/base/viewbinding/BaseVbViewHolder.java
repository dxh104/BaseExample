package com.dxh.base_library_module.base.viewbinding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.viewbinding.ViewBinding;


/*
 * Create by XHD on 2022/07/27
 * Description:
 */
public class BaseVbViewHolder<BaseViewBinding extends ViewBinding> extends RecyclerView.ViewHolder {
    private BaseViewBinding mBinding;

    public BaseVbViewHolder(@NonNull BaseViewBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public BaseViewBinding getBinding() {
        return mBinding;
    }
}
