package com.lfp.lfp_base_recycleview_library;

import android.content.Context;

import java.util.List;

/**
 * Created by lfp on 2017/5/31.
 * 简单适配器,局部更新
 */

public abstract class HytchAdapter<T> extends CommonAdapter<T> {

  public HytchAdapter(Context context, int layoutId, List<T> dataList) {
    super(context, layoutId, dataList);
  }

  @Override
  public boolean areItemsTheSame(T item1, T item2) {
    return false;
  }

  @Override
  public boolean areContentsTheSame(T item1, T item2) {
    return false;
  }

  @Override
  public T getChangePayload(List<T> oldList, int oldItemPosition, List<T> newList, int newItemPosition) {
    return null;
  }
}
