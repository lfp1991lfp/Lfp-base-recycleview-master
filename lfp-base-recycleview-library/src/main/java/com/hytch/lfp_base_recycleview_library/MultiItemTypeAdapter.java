package com.hytch.lfp_base_recycleview_library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hytch.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.hytch.lfp_base_recycleview_library.base.ItemViewDelegateManager;
import com.hytch.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfp on 2017/4/25.
 * 多视图适配器
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<LfpViewHolder> {

  private final Context context;
  private List<T> dataList;
  private ItemViewDelegateManager<T> itemViewDelegateManager;
  private OnItemClickListener onItemClickListener;

  public MultiItemTypeAdapter(Context context, List<T> dataList) {
    this.context = context;
    this.dataList = dataList == null ? new ArrayList<T>() : new ArrayList<>(dataList);
    this.itemViewDelegateManager = new ItemViewDelegateManager<>();
  }

  @Override
  public int getItemViewType(int position) {
    if (!useItemViewDelegateManager()) {
      return super.getItemViewType(position);
    }
    return itemViewDelegateManager.getItemViewType(dataList.get(position), position);
  }


  @Override
  public LfpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemViewDelegate itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
    int layoutId = itemViewDelegate.getItemViewLayoutId();
    LfpViewHolder holder = LfpViewHolder.createViewHolder(context, parent, layoutId);
    onViewHolderCreated(holder, holder.getConvertView());
    setListener(parent, holder, viewType);
    return holder;
  }

  public void onViewHolderCreated(LfpViewHolder holder, View itemView) {

  }

  public void convert(LfpViewHolder holder, T item) {
    itemViewDelegateManager.convert(holder, item, holder.getAdapterPosition());
  }

  protected boolean isEnabled(int viewType) {
    return true;
  }


  protected void setListener(final ViewGroup parent, final LfpViewHolder viewHolder, int viewType) {
    if (!isEnabled(viewType)) return;
    viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onItemClickListener != null) {
          int position = viewHolder.getAdapterPosition();
          onItemClickListener.onItemClick(v, viewHolder, position);
        }
      }
    });

    viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        if (onItemClickListener != null) {
          int position = viewHolder.getAdapterPosition();
          return onItemClickListener.onItemLongClick(v, viewHolder, position);
        }
        return false;
      }
    });
  }

  @Override
  public void onBindViewHolder(LfpViewHolder holder, int position) {
    convert(holder, dataList.get(position));
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }


  public List<T> getDataList() {
    return dataList;
  }

  public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
    itemViewDelegateManager.addDelegate(itemViewDelegate);
    return this;
  }

  public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T>
      itemViewDelegate) {
    itemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
    return this;
  }

  protected boolean useItemViewDelegateManager() {
    return itemViewDelegateManager.getItemViewDelegateCount() > 0;
  }

  public interface OnItemClickListener {
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

    boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
