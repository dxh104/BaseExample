package com.dxh.base_library_module.base.viewbinding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
 * Create by XHD on 2022/07/27
 * Description:
 */
public abstract class BaseVbRecycleAdapter<BaseViewBinding extends ViewBinding, T> extends RecyclerView.Adapter<BaseVbViewHolder> {
    public List<T> mDatas;//数据集合
    protected Context mContext;//上下文
    protected OnItemClickListner onItemClickListner;//条目单击事件

    public BaseVbRecycleAdapter(List<T> mDatas) {
        this.mDatas = new ArrayList<>();
        if (mDatas != null)
            this.mDatas.addAll(mDatas);//非null，追加初始化数据
        setHasStableIds(true);//配合重写getItemId,避免刷新时图片闪烁
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = setItemViewType(mDatas, position);//------设置条目布局类型------
        if (itemViewType != 0) {
            return itemViewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseVbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewBinding mViewBinding = null;
        mContext = parent.getContext();
        //返回当前类的父类的Type，也就是BaseActivity
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {//如果支持泛型
            Class<BaseViewBinding> clazz = (Class<BaseViewBinding>) ((ParameterizedType) type).getActualTypeArguments()[0];
            try {
                //反射inflate
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                mViewBinding = (BaseViewBinding) method.invoke(null, LayoutInflater.from(mContext));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        final BaseVbViewHolder baseVbViewHolder = new BaseVbViewHolder(mViewBinding);
        mViewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null)
                    onItemClickListner.onItemClickListner(v, baseVbViewHolder.getLayoutPosition());
            }
        });
        setListener(baseVbViewHolder, (BaseViewBinding) baseVbViewHolder.getBinding(), mDatas);//------设置监听器------
        return baseVbViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseVbViewHolder holder, final int position) {
        bindData(holder, position, (BaseViewBinding) holder.getBinding(), mDatas.get(position));//------绑定数据------
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //可以做一些回收之前可以做一些释放资源,取消加载等功能
    @Override
    public void onViewRecycled(@NonNull BaseVbViewHolder holder) {
        super.onViewRecycled(holder);
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        if (datas != null)
            this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        if (datas != null)
            this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(T datas) {
        this.mDatas.add(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     *
     * @param datas
     */
    public void addDataWithoutAnim(List<T> datas) {
        if (datas == null)
            return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }

    /**
     * 返回ItemView布局类型---子类实现可以在外部用个变量保存返回值，初始化不同布局控件和点击事件可以直接根据这个返回值判断
     *
     * @return
     */
    protected abstract int setItemViewType(List<T> mDatas, int position);

    /**
     * 设置监听事件
     */
    protected abstract void setListener(BaseVbViewHolder holder, BaseViewBinding binding, List<T> mDatas);

    /**
     * 绑定数据
     *
     * @param position 对应的索引
     * @param t
     */
    protected abstract void bindData(BaseVbViewHolder holder, int position, BaseViewBinding binding, T t);


    /**
     * 设置文本属性
     *
     * @param view
     * @param text
     */
    public void setItemText(View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 设置图片属性
     *
     * @param view
     * @param url
     */
    public void setItemImage(View view, Object url) {
        if (view instanceof ImageView && mContext != null) {
            ImageView imageView = (ImageView) view;
            Glide.with(mContext).asDrawable().load(url).into(imageView);
        }
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }
}
