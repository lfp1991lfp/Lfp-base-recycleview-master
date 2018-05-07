package hytch.com.lfp_base_recycleview_master.snap.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lfp.lfp_base_recycleview_library.HytchAdapter;
import com.lfp.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.List;

import hytch.com.lfp_base_recycleview_master.R;
import hytch.com.lfp_base_recycleview_master.scroll.util.RecyclerScaleUtils;
import hytch.com.lfp_base_recycleview_master.scroll.util.ScreenUtils;
import hytch.com.lfp_base_recycleview_master.snap.bean.SnapBean;

/**
 * lfp
 * 2018/4/23
 * 测试适配器
 */
public class SnapHelperAdapter extends HytchAdapter<SnapBean> {

    public SnapHelperAdapter(Context context, int layoutId, List<SnapBean> dataList) {
        super(context, layoutId, dataList);
    }

    @Override
    public void onViewHolderCreated(ViewGroup parent, LfpViewHolder holder, View itemView) {
        //这里设置view的宽度
        RecyclerScaleUtils.onCreateViewHolder(parent, itemView, ScreenUtils.dip2px(context, 30f));
    }

    @Override
    protected void convert(LfpViewHolder holder, SnapBean item, int position) {
//        holder.setText(R.id.txt_id, item.getContent());
        holder.setImageResource(R.id.img_id, item.getImg());
    }
}
