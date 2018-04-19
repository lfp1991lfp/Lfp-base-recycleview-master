package com.lfp.lfp_base_recycleview_library;

import android.content.Context;

import java.util.List;

/**
 * Created by lfp on 2017/5/31.
 * 简单适配器
 */

public abstract class HytchAdapter<T> extends CommonAdapter<T> {

  public HytchAdapter(Context context, int layoutId, List<T> dataList) {
    super(context, layoutId, dataList);
  }

  @Override
  protected boolean areItemsTheSame(T item1, T item2) {
    return false;
  }

  @Override
  protected boolean areContentsTheSame(T item1, T item2) {
    return false;
  }
}
