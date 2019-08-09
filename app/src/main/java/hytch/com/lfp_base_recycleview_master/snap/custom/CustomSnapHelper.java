package hytch.com.lfp_base_recycleview_master.snap.custom;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import hytch.com.lfp_base_recycleview_master.listener.RecyclerPageTransformer;

/**
 * lfp
 * 2018/4/24
 * 自定义SnapHelp
 */
public class CustomSnapHelper extends PagerSnapHelper {

    private RecyclerView recyclerView;
    private RecyclerPageTransformer recyclerPageTransformer;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerPageTransformer != null && recyclerView != null) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
//
//                final int childCount = layoutManager.getChildCount();
//                for (int i = 0; i < childCount; i++) {
//                    final View child = layoutManager.getChildAt(i);
//
//                    // TODO: 2018/4/24 滚动距离
//                    final float transformPos = (float) (child.getLeft() / getClientWidth());
////                    Log.e("CustomSnapHelp", String.format("%d, %s", recyclerView
// .computeHorizontalScrollOffset(), "recyclerView.computeHorizontalScrollOffset()"));
//                    Log.e("CustomSnapHelp", String.format("%.2f, %s", transformPos,
//                    "transformPos"));
//                    recyclerPageTransformer.transformPage(child, transformPos);
//                }
                final int childCount = recyclerView.getChildCount();
                Log.e("tag", childCount + "");
                for (int i = 0; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);

                    int left = child.getLeft();
                    int right = 1080 - child.getRight();
                    final float percent = left < 0 || right < 0 ? 0 :
                            Math.min(left, right) * 1f / Math.max(left, right);
                    Log.e("tag", "percent = " + percent);
                    float scaleFactor = 0.85f + Math.abs(percent) * (1.0f - 0.85f);
                    child.setScaleY(scaleFactor);
                }
            }
        }
    };

    private float getClientWidth() {
        return recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight();
    }

    public void setPageTransformer(RecyclerPageTransformer recyclerPageTransformer) {
        this.recyclerPageTransformer = recyclerPageTransformer;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        if (recyclerView == null) {
            return;
        }
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(onScrollListener);
    }
}
