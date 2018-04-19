package hytch.com.lfp_base_recycleview_master.view.headview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfp.lfp_base_recycleview_library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by linfp on 2016/4/8.
 * 刷新动画的头部(自定义)
 */
public class RefreshHeadView extends FrameLayout implements PtrUIHandler {

  private final static String TAG = RefreshHeadView.class.getSimpleName();

  private String pullDownRefreshText = "下拉刷新";
  private String releaseRefreshText = "释放更新";
  private String refreshingText = "加载中...";
  private String refreshCompleteText = "刷新完成";

  private TextView tvHeadTitle;    //头部下拉后,显示的文字
  private ImageView headImage;    //下拉的动画显示

  public RefreshHeadView(Context context) {
    //super(context);
    this(context, null);
  }

  public RefreshHeadView(Context context, AttributeSet attrs) {
    //super(context, attrs);
    this(context, attrs, 0);
  }

  public RefreshHeadView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initView(context);
  }

  private void initView(Context context) {
    ViewGroup headView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout
        .refresh_header_normal, this, true);
    tvHeadTitle = (TextView) headView.findViewById(R.id.tv_normal_refresh_header_status);
    headImage = (ImageView) headView.findViewById(R.id.iv_normal_refresh_header);
  }

  @Override
  public void onUIReset(PtrFrameLayout frame) {
    headImage.setVisibility(View.INVISIBLE);
    tvHeadTitle.setText(pullDownRefreshText);
    setHeaderFlipState(false);
  }

  @Override
  public void onUIRefreshPrepare(PtrFrameLayout frame) {
    headImage.setVisibility(View.VISIBLE);
    tvHeadTitle.setText(pullDownRefreshText);
    setHeaderFlipState(false);
  }

  @Override
  public void onUIRefreshBegin(PtrFrameLayout frame) {
    headImage.setVisibility(View.VISIBLE);
    tvHeadTitle.setText(refreshingText);
    setHeaderFlipState(true);
  }

  @Override
  public void onUIRefreshComplete(PtrFrameLayout frame) {
    headImage.setVisibility(View.INVISIBLE);
    tvHeadTitle.setText(refreshCompleteText);
    setHeaderFlipState(false);
  }

  @Override
  public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status,
                                 PtrIndicator ptrIndicator) {
    final int mOffsetToRefresh = frame.getOffsetToRefresh();
    final int currentPos = ptrIndicator.getCurrentPosY();
    final int lastPos = ptrIndicator.getLastPosY();

    if (currentPos < mOffsetToRefresh && mOffsetToRefresh <= lastPos) {
      if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
        headImage.setVisibility(View.VISIBLE);
        tvHeadTitle.setText(pullDownRefreshText);
        setHeaderFlipState(false);
      }
    } else if (mOffsetToRefresh < currentPos && lastPos <= mOffsetToRefresh) {
      if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
        headImage.setVisibility(View.VISIBLE);
        tvHeadTitle.setText(releaseRefreshText);
        setHeaderFlipState(true);
      }
    }
  }

  /**
   * 设置未满足刷新条件，提示继续往下拉的文本.
   *
   * @param pullDownRefreshText 提示文本
   */
  public void setPullDownRefreshText(String pullDownRefreshText) {
    this.pullDownRefreshText = pullDownRefreshText;
  }

  /**
   * 设置满足刷新条件时的文本.
   *
   * @param releaseRefreshText 提示文本
   */
  public void setReleaseRefreshText(String releaseRefreshText) {
    this.releaseRefreshText = releaseRefreshText;
  }

  /**
   * 设置正在刷新时的文本.
   *
   * @param refreshingText 提示文本
   */
  public void setRefreshingText(String refreshingText) {
    this.refreshingText = refreshingText;
  }

  /**
   * 设置刷新完成的文内容.
   *
   * @param refreshCompleteText 提示文本
   */
  public void setRefreshCompleteText(String refreshCompleteText) {
    this.refreshCompleteText = refreshCompleteText;
  }

  private void setHeaderFlipState(boolean animation) {
    if (animation) {
      headImage.setImageResource(R.drawable.refresh_head_anim);
      AnimationDrawable animationDrawable = (AnimationDrawable) headImage.getDrawable();
      animationDrawable.start();
    } else {
      headImage.setImageResource(R.mipmap.refreshing_image_01);
    }
  }
}
