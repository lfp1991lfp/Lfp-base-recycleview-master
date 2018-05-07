package hytch.com.lfp_base_recycleview_master.snap.transformat;

import android.view.View;

import hytch.com.lfp_base_recycleview_master.listener.RecyclerPageTransformer;

/**
 * lfp
 * 2018/4/2
 * 自定义切换动画
 */
public class CustomTransformer implements RecyclerPageTransformer {

    private static final float MIN_SCALE = 0.89F;

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            //
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);

            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        } else {
            page.setScaleY(MIN_SCALE);
        }
    }
}
