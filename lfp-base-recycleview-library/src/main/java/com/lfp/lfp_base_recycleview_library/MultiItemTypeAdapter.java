package com.lfp.lfp_base_recycleview_library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.lfp.lfp_base_recycleview_library.base.ItemViewDelegateManager;
import com.lfp.lfp_base_recycleview_library.base.LfpViewHolder;
import com.lfp.lfp_base_recycleview_library.callback.DataCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfp on 2017/4/25.
 * 多视图适配器
 */

@SuppressWarnings("unchecked")
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<LfpViewHolder> {

    protected final Context context;
    private List<T> dataList;
    private ItemViewDelegateManager<T> itemViewDelegateManager;
    private OnItemClickListener onItemClickListener;

    public MultiItemTypeAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList != null ? dataList : new ArrayList<T>();
        this.itemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            return super.getItemViewType(position);
        }
        return itemViewDelegateManager.getItemViewType(dataList.get(position), position);
    }


    @NonNull
    @Override
    public LfpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        LfpViewHolder holder = LfpViewHolder.createViewHolder(context, parent, layoutId);
        onViewHolderCreated(parent, holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewGroup parent, LfpViewHolder holder, View itemView) {

    }

    public void convert(LfpViewHolder holder, T item) {
        itemViewDelegateManager.convert(holder, item, holder.getAdapterPosition());
    }

    /**
     * 供外层复写，若是类型不允许点击，则复写该方法.
     *
     * @param viewType 布局类型
     * @return true表示可以点击, false表示不可以点击
     */
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
    public void onBindViewHolder(@NonNull LfpViewHolder holder, int position) {
        convert(holder, dataList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull LfpViewHolder holder, int position, List<Object> payloads) {
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
    public List<T> getDataList() {
        return dataList;
    }

    private void setDataList(List<T> newDataList) {
        this.dataList.clear();
        this.dataList.addAll(newDataList);
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

    public void setDateResult(List<T> newList) {
        DiffUtil.DiffResult diffResult = getDiffResult(newList);
        refresh(diffResult, newList);
    }

    /**
     * 提供数据差异性.
     *
     * @param newList 新数据源
     * @return 数据差异
     */
    public DiffUtil.DiffResult getDiffResult(List<T> newList) {
        return DiffUtil.calculateDiff(new DataCallBack(this, dataList, newList));
    }

    /**
     * 刷新数据，并重置之前的数据.
     *
     * @param diffResult 数据差异
     * @param newList    新数据
     */
    public void refresh(DiffUtil.DiffResult diffResult, List<T> newList) {
        diffResult.dispatchUpdatesTo(this);
        setDataList(newList);
    }

    @SuppressWarnings("unused")
    public void addAll(List<T> newList) {
        int position = dataList.size();
        dataList.addAll(newList);
        notifyItemInserted(position);
    }

    @SuppressWarnings("unused")
    public void addAll(int position, List<T> newList) {
        dataList.addAll(position, newList);
        notifyItemInserted(position);
    }

    /**
     * 局部刷新
     *
     * @param oldItemPosition 旧的位置
     * @param newItemPosition 新的位置
     * @return 变换的数据源
     */
    public abstract T getChangePayload(
            List<T> oldList, int oldItemPosition, List<T> newList, int newItemPosition);

    /**
     * 比较唯一吗相同，一般是ID.
     *
     * @param item1 value1
     * @param item2 value2
     * @return true表示一样，否则不是不一样
     */
    public abstract boolean areItemsTheSame(T item1, T item2);

    /**
     * 若id一样，则比较下一个内容
     *
     * @param item1 value1
     * @param item2 value2
     * @return true表示一样，否则不是不一样
     */
    public abstract boolean areContentsTheSame(T item1, T item2);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
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

//    private class DataCallBack extends DiffUtil.Callback {
//
//        private List<T> oldDataList;
//        private List<T> newDataList;
//
//        public DataCallBack(List<T> oldDataList, List<T> newDataList) {
//            this.oldDataList = oldDataList;
//            this.newDataList = newDataList;
//        }
//
//        @Override
//        public int getOldListSize() {
//            return oldDataList.size();
//        }
//
//        @Override
//        public int getNewListSize() {
//            return newDataList.size();
//        }
//
//        /**
//         * 比较两个集合中旧位置和新位置的值是否一致.
//         *
//         * @param oldItemPosition 旧位置
//         * @param newItemPosition 新位置
//         * @return true则执行areContentsTheSame方法
//         */
//        @Override
//        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//            return MultiItemTypeAdapter.this.areItemsTheSame(oldDataList.get(oldItemPosition),
//                    newDataList.get(newItemPosition));
//        }
//
//        /**
//         * This method is called only if {@link #areItemsTheSame(int, int)} returns
//         * {@code true} for these items.
//         */
//        @Override
//        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//            return MultiItemTypeAdapter.this.areContentsTheSame(oldDataList.get(oldItemPosition),
//                    newDataList.get(newItemPosition));
//        }
//
//        /**
//         * 局部刷新.
//         *
//         * @param oldItemPosition 旧位置
//         * @param newItemPosition 新位置
//         * @return 需要刷新的item
//         */
//        @Nullable
//        @Override
//        public T getChangePayload(int oldItemPosition, int newItemPosition) {
//            return MultiItemTypeAdapter.this.getChangePayload(oldDataList, oldItemPosition,
//                    newDataList, newItemPosition);
//        }
//    }
}
