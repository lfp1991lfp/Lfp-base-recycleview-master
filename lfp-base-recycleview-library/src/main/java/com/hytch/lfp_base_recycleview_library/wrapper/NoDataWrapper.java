package com.hytch.lfp_base_recycleview_library.wrapper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lfp on 2017/4/28.
 * 没有数据的adapter
 */

public class NoDataWrapper<T> extends EmptyWrapper {

  private int noData = 0;
  private View noDataView;

  public NoDataWrapper(RecyclerView.Adapter adapter) {
    super(adapter);
  }

  @Override
  public int emptyResId() {
    return noData;
  }

  @Override
  public View emptyView() {
    return noDataView;
  }

  public void setNoData(int noData) {
    this.noData = noData;
  }

  public void setNoDataView(View noDataView) {
    this.noDataView = noDataView;
  }
}
