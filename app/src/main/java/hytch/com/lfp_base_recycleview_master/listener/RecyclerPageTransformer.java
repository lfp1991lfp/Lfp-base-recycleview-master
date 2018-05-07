package hytch.com.lfp_base_recycleview_master.listener;

import android.view.View;

/**
 * lfp
 * 2018/4/24
 * recyclerView横向布局滑动动画切换
 */
public interface RecyclerPageTransformer {

    /**
     * Apply a property transformation to the given page.
     *
     * @param page     Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     *                 page position to the right, and -1 is one page position to the left.
     */
    void transformPage(View page, float position);
}
