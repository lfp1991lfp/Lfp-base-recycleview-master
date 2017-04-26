package com.hytch.lfp_base_recycleview_library.base;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by lfp on 2017/4/25.
 * 视图子选项代理类
 */

public class ItemViewDelegateManager<T> {

  private SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<>();

  public int getItemViewDelegateCount() {
    return delegates.size();
  }

  /**
   * 添加代理的item.
   *
   * @param delegate 代理选项
   * @return this
   */
  public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
    int viewType = delegates.size();
    if (delegate != null) {
      delegates.put(viewType, delegate);
    }
    return this;
  }

  /**
   * 添加代理item.
   *
   * @param viewType 代理类型
   * @param delegate 代理选项
   * @return this
   */
  public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
    if (delegates.get(viewType) != null) {
      throw new IllegalArgumentException(
          "An ItemViewDelegate is already registered for the viewType = "
              + viewType
              + ". Already registered ItemViewDelegate is "
              + delegates.get(viewType));
    }
    delegates.put(viewType, delegate);
    return this;
  }

  /**
   * 移除代理.
   *
   * @param delegate 代理选项
   * @return this
   */
  public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
    if (delegate == null) {
      throw new NullPointerException("ItemViewDelegate is null");
    }
    int indexToRemove = delegates.indexOfValue(delegate);

    if (indexToRemove >= 0) {
      delegates.removeAt(indexToRemove);
    }
    return this;
  }

  /**
   * 移除代理.
   *
   * @param itemType 代理类型
   * @return this
   */

  public ItemViewDelegateManager<T> removeDelegate(int itemType) {
    int indexToRemove = delegates.indexOfKey(itemType);

    if (indexToRemove >= 0) {
      delegates.removeAt(indexToRemove);
    }
    return this;
  }

  /**
   * 获取item的类型选项.
   *
   * @param item     子布局
   * @param position 位置
   * @return 类型
   */
  public int getItemViewType(T item, int position) {
    int delegatesCount = delegates.size();
    for (int i = delegatesCount - 1; i >= 0; i--) {
      ItemViewDelegate<T> delegate = delegates.valueAt(i);
      if (delegate.isForViewType(item, position)) {
        return delegates.keyAt(i);
      }
    }
    throw new IllegalArgumentException(
        "No ItemViewDelegate added that matches position=" + position + " in data source");
  }

  /**
   * 转换.
   *
   * @param holder   viewHolder
   * @param item     内容布局
   * @param position 位置
   */
  public void convert(LfpViewHolder holder, T item, int position) {
    int delegatesCount = delegates.size();
    for (int i = 0; i < delegatesCount; i++) {
      ItemViewDelegate<T> delegate = delegates.valueAt(i);

      if (delegate.isForViewType(item, position)) {
        delegate.convert(holder, item, position);
        return;
      }
    }
    throw new IllegalArgumentException(
        "No ItemViewDelegateManager added that matches position=" + position + " in data source");
  }

  /**
   * 获取代理类型.
   *
   * @param viewType 视图类型
   * @return 代理的视图
   */
  public ItemViewDelegate getItemViewDelegate(int viewType) {
    return delegates.get(viewType);
  }

  /**
   * 根据视图类型选择视图代理.
   *
   * @param viewType 类型ID
   * @return 当前所选中的item
   */
  public int getItemViewLayoutId(int viewType) {
    return getItemViewDelegate(viewType).getItemViewLayoutId();
  }

  /**
   * 代理所在的位置.
   *
   * @param itemViewDelegate 代理所在的位置
   * @return position
   */
  public int getItemViewType(ItemViewDelegate<T> itemViewDelegate) {
    return delegates.indexOfValue(itemViewDelegate);
  }
}
