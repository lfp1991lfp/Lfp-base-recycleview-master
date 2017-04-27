package com.hytch.lfp_base_recycleview_library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hytch.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.hytch.lfp_base_recycleview_library.base.ItemViewDelegateManager;
import com.hytch.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.List;

/**
 * Created by lfp on 2017/4/25.
 * 多视图适配器
 */

public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<LfpViewHolder> {

  private final Context context;
  protected SparseArray<T> dataList;
  private ItemViewDelegateManager<T> itemViewDelegateManager;
  private OnItemClickListener onItemClickListener;

  public MultiItemTypeAdapter(Context context, SparseArray<T> dataList) {
    this.context = context;
    this.dataList = (dataList == null ? new SparseArray<T>() : dataList);
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
  public void onBindViewHolder(LfpViewHolder holder, int position, List<Object> payloads) {
    onBindViewHolder(holder, position);
  }

  @Override
  public int getItemCount() {
    return dataList.size();
  }


  /**
   * 获取数据集.
   *
   * @return 数据集的集合
   */
  public SparseArray<T> getDataList() {
    return dataList;
  }

  public void setDataList(SparseArray<T> newDataList) {
    this.dataList = newDataList;
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

  /**
   * Simple implementation of the {@link OnItemClickListener} interface with stub
   * implementations of each method. Extend this if you do not intend to override
   * every method of {@link OnItemClickListener}.
   */
  public static class SimpleOnItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
      return false;
    }
  }

  public void setDateResult(SparseArray<T> newSparseArray) {
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DataCallBack(dataList,
        newSparseArray));
    diffResult.dispatchUpdatesTo(this);
    setDataList(newSparseArray);
  }

  private class DataCallBack extends DiffUtil.Callback {

    private SparseArray<T> oldDataList;
    private SparseArray<T> newDataList;

    public DataCallBack(SparseArray<T> oldDataList, SparseArray<T> newDataList) {
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

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return MultiItemTypeAdapter.this.areItemsTheSame(oldDataList.get(oldItemPosition),
          newDataList.get(newItemPosition));
    }

    /**
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return MultiItemTypeAdapter.this.areContentsTheSame(oldDataList.get(oldItemPosition),
          newDataList.get(newItemPosition));
    }

    @Nullable
    @Override
    public T getChangePayload(int oldItemPosition, int newItemPosition) {
      return MultiItemTypeAdapter.this.getChangePayload(oldDataList, oldItemPosition, newDataList, newItemPosition);
    }
  }

  /**
   * 局部刷新
   *
   * @param oldItemPosition 旧的位置
   * @param newItemPosition 新的位置
   * @return
   */
  protected T getChangePayload(SparseArray<T> oldList, int oldItemPosition, SparseArray<T> newList, int newItemPosition) {
    return null;
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

    boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
  }

  /**
   * 比较唯一吗相同，一般是ID.
   *
   * @param item1 value1
   * @param item2 value2
   * @return true表示一样，否则不是不一样
   */
  protected abstract boolean areItemsTheSame(T item1, T item2);

  /**
   * 若id一样，则比较下一个内容
   *
   * @param item1 value1
   * @param item2 value2
   * @return true表示一样，否则不是不一样
   */
  protected abstract boolean areContentsTheSame(T item1, T item2);
}
