package com.hytch.lfp_base_recycleview_library;

import android.content.Context;
import android.view.LayoutInflater;

import com.hytch.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.hytch.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.List;

/**
 * Created by lfp on 2017/4/25.
 * 通用适配器
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
  protected Context context;
  protected int layoutId;
  protected List<T> dataList;
  protected LayoutInflater inflater;

  public CommonAdapter(final Context context, final int layoutId, List<T> dataList) {
    super(context, dataList);
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.layoutId = layoutId;
    this.dataList = dataList;

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
      public void convert(LfpViewHolder holder, T t, int position) {
        CommonAdapter.this.convert(holder, t, position);
      }
    });
  }

  protected abstract void convert(LfpViewHolder holder, T t, int position);
}
