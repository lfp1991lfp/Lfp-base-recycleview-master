package com.lfp.lfp_base_recycleview_library.base;

/**
 * Created by lfp on 2017/4/25.
 * 视图view的代理
 */

public interface ItemViewDelegate<T> {

  int getItemViewLayoutId();

  boolean isForViewType(T item, int position);

  void convert(LfpViewHolder viewHolder, T item, int position);
}
