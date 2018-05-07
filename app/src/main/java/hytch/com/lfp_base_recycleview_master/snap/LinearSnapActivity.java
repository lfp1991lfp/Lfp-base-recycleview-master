package hytch.com.lfp_base_recycleview_master.snap;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hytch.com.lfp_base_recycleview_master.R;
import hytch.com.lfp_base_recycleview_master.snap.adapter.SnapHelperAdapter;
import hytch.com.lfp_base_recycleview_master.snap.bean.SnapBean;

/**
 * lfp
 * 2018/4/23
 * LinearSnapHelper demo
 */
public class LinearSnapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_snap);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(20f)));
        recyclerView.setAdapter(new SnapHelperAdapter(this, R.layout.my_recyclerview_item, initData()));

//        CustomSnapHelper snapHelper = new CustomSnapHelper();   //能够快速滑动，滑动超过后，位置居中
        PagerSnapHelper snapHelper = new PagerSnapHelper();   //一次只能滑动一页，不能快速滑动

//        snapHelper.setPageTransformer(new CustomTransformer());
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.scrollToPosition(1);
    }

    private List<SnapBean> initData() {
        ArrayList<SnapBean> list = new ArrayList<>();
        TypedArray mTypedArray = getResources().obtainTypedArray(R.array.img_array);
        int length = getResources().getIntArray(R.array.img_array).length;
        for (int i = 0; i < length; i++) {
            SnapBean snapBean = new SnapBean();
            int imageId = mTypedArray.getResourceId(i, R.mipmap.m_1);//第一个参数为 ：所取图片在数组中的索引，第二个参数为：未找到时，返回的默认值id。
            snapBean.setImg(imageId);
            snapBean.setContent("测试内容" + i);

            list.add(snapBean);
        }

        mTypedArray.recycle();//用完记得要recycle

        return list;
    }
}
