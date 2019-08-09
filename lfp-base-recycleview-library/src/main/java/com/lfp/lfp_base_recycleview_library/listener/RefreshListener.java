package com.lfp.lfp_base_recycleview_library.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by linfp on 2016/4/8.
 * 抽象化两个刷新接口
 */
public interface RefreshListener {

    void onRefresh(RecyclerView recyclerView);

    void onLoadMore(RecyclerView recyclerView);
}
