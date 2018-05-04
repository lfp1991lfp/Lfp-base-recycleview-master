package com.lfp.lfp_base_recycleview_library.async;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.lfp_base_recycleview_library.MultiItemTypeAdapter;
import com.lfp.lfp_base_recycleview_library.base.ItemViewDelegate;
import com.lfp.lfp_base_recycleview_library.base.ItemViewDelegateManager;
import com.lfp.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * lfp
 * 2018/4/28
 * 异步加载数据
 */
@SuppressWarnings("all")
public abstract class AsyncMultiItemTypeAdapter<T> extends ListAdapter<T, LfpViewHolder> {

    protected final Context context;
    private List<T> dataList;
    private ItemViewDelegateManager<T> itemViewDelegateManager;
    private MultiItemTypeAdapter.OnItemClickListener onItemClickListener;

    protected AsyncMultiItemTypeAdapter(Context context, List<T> dataList, DiffUtil.ItemCallback<T> itemCallback) {
        super(itemCallback);
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

    @Override
    public void onBindViewHolder(@NonNull LfpViewHolder holder, int position) {
        convert(holder, dataList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull LfpViewHolder holder, int position, List<Object> payloads) {
        onBindViewHolder(holder, position);
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//
//        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void convert(LfpViewHolder holder, T item) {
        itemViewDelegateManager.convert(holder, item, holder.getAdapterPosition());
    }

    /**
     * 获取数据集.
     *
     * @return 数据集的集合
     */
    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> newDataList) {
        this.dataList.clear();
        this.dataList.addAll(newDataList);
    }

    @SuppressWarnings("unused")
    public void addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        itemViewDelegateManager.addDelegate(itemViewDelegate);
    }

    @SuppressWarnings("unused")
    public void addItemViewDelegate(int viewType, ItemViewDelegate<T>
            itemViewDelegate) {
        itemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
    }

    public void onViewHolderCreated(ViewGroup parent, LfpViewHolder holder, View itemView) {

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

    @SuppressWarnings("unused")
    public void setOnItemClickListener(MultiItemTypeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected boolean useItemViewDelegateManager() {
        return itemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @SuppressWarnings("unused")
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }
}
