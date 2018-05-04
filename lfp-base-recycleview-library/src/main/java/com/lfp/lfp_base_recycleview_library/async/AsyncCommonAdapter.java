package com.lfp.lfp_base_recycleview_library.async;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;

import com.lfp.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.lfp.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.List;

/**
 * Created by lfp on 2017/4/25.
 * 通用适配器
 */
public abstract class AsyncCommonAdapter<T> extends AsyncMultiItemTypeAdapter<T> {
    protected int layoutId;
    protected LayoutInflater inflater;

    public AsyncCommonAdapter(
            final Context context, final int layoutId,
            List<T> dataList, DiffUtil.ItemCallback<T> itemCallback) {
        super(context, dataList, itemCallback);
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(LfpViewHolder holder, T item, int position) {
                AsyncCommonAdapter.this.convert(holder, item, position);
            }
        });
    }

    protected abstract void convert(LfpViewHolder holder, T item, int position);
}
