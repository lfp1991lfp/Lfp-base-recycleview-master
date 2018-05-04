package com.lfp.lfp_base_recycleview_library;

import android.content.Context;

import java.util.List;

/**
 * Created by lfp on 2017/5/31.
 * 简单适配器
 */

public abstract class MultiItemHytchAdapter<T> extends MultiItemTypeAdapter<T> {

  public MultiItemHytchAdapter(Context context, List<T> dataList) {
    super(context, dataList);
  }

  @Override
  public boolean areItemsTheSame(T item1, T item2) {
    return false;
  }

  @Override
  public boolean areContentsTheSame(T item1, T item2) {
    return false;
  }
}
