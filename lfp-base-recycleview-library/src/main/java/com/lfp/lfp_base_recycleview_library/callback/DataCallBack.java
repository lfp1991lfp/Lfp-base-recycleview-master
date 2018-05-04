package com.lfp.lfp_base_recycleview_library.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.lfp.lfp_base_recycleview_library.MultiItemTypeAdapter;

import java.util.List;

/**
 * lfp
 * 2018/4/28
 * 同步数据更新
 */
@SuppressWarnings("unchecked")
public class DataCallBack<T> extends DiffUtil.Callback {

    private List<T> oldDataList;
    private List<T> newDataList;

    private MultiItemTypeAdapter adapter;

    public DataCallBack(MultiItemTypeAdapter adapter, List<T> oldDataList, List<T> newDataList) {
        this.adapter = adapter;
        this.oldDataList = oldDataList;
        this.newDataList = newDataList;
    }

    @Override
    public int getOldListSize() {
        return oldDataList.size();
    }

    @Override
    public int getNewListSize() {
        return newDataList.size();
    }

    /**
     * 比较两个集合中旧位置和新位置的值是否一致.
     *
     * @param oldItemPosition 旧位置
     * @param newItemPosition 新位置
     * @return true则执行areContentsTheSame方法
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return adapter.areItemsTheSame(oldDataList.get(oldItemPosition),
                newDataList.get(newItemPosition));
    }

    /**
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return adapter.areContentsTheSame(oldDataList.get(oldItemPosition),
                newDataList.get(newItemPosition));
    }

    /**
     * 局部刷新.
     *
     * @param oldItemPosition 旧位置
     * @param newItemPosition 新位置
     * @return 需要刷新的item
     */
    @Nullable
    @Override
    public T getChangePayload(int oldItemPosition, int newItemPosition) {
        return (T) adapter.getChangePayload(oldDataList, oldItemPosition,
                newDataList, newItemPosition);
    }
}
