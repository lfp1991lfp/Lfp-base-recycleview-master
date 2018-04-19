package com.lfp.lfp_base_recycleview_library.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by lfp on 2017/4/25.
 * ViewHolder继承RecyclerView.Holder
 */

@SuppressWarnings({"unchecked", "unused"})
public final class LfpViewHolder extends RecyclerView.ViewHolder {

  private final SparseArray<View> viewSparseArray;   //保存每一个子选项的布局视图
  private final Context context;
  private final View convertView;

  /**
   * .
   * 初始化构造器，实现数据绑定
   *
   * @param context  上下文
   * @param itemView 绑定的view
   */
  private LfpViewHolder(final Context context, final View itemView) {
    super(itemView);
    this.context = context;
    this.convertView = itemView;
    viewSparseArray = new SparseArray<>();
  }

  /**
   * 绑定View视图.
   *
   * @param context  上下文
   * @param itemView 子布局
   * @return 绑定holder
   */
  public static LfpViewHolder createViewHolder(final Context context, final View itemView) {
    return new LfpViewHolder(context, itemView);
  }


  /**
   * 绑定View视图.
   *
   * @param context  上下文
   * @param parent   子布局
   * @param layoutId 资源ID
   * @return 绑定holder
   */
  public static LfpViewHolder createViewHolder(final Context context,
                                               final ViewGroup parent,
                                               final int layoutId) {
    View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
    return new LfpViewHolder(context, view);
  }

  /**
   * 获取需要抓换的视图.
   *
   * @return 布局
   */
  public View getConvertView() {
    return convertView;
  }

  /**
   * 获取view.
   *
   * @param viewId 控件ID
   * @param <T>    转换的泛型
   * @return T
   */
  private <T extends View> T retrieveView(final int viewId) {
    View view = viewSparseArray.get(viewId);
    if (view == null) {
      view = itemView.findViewById(viewId);
      viewSparseArray.put(viewId, view);
    }
    return (T) view;
  }

  /**
   * 根据ViewId获取当前子布局中的其他控件.
   *
   * @param viewId id
   * @param <T>    泛型
   * @return T
   */
  public <T extends View> T getView(final int viewId) {
    return retrieveView(viewId);
  }

  /**
   * Will set the text of a TextView.
   *
   * @param viewId The view id.
   * @param value  The text to put in the text view.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setText(final int viewId, final String value) {
    TextView view = retrieveView(viewId);
    view.setText(value);
    return this;
  }

  /**
   * Will set the text of a TextView.
   *
   * @param viewId The view id.
   * @param resId  The text to put in the text view.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setText(final int viewId, final int resId) {
    TextView view = retrieveView(viewId);
    view.setText(resId);
    return this;
  }

  /**
   * @param viewId The view id.
   * @param value  The text to put in the text view.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setText(final int viewId, final CharSequence value) {
    TextView view = retrieveView(viewId);
    view.setText(value);
    return this;
  }

  /**
   * Will set the image of an ImageView from a resource id.
   *
   * @param viewId     The view id.
   * @param imageResId The image resource id.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setImageResource(final int viewId, final int imageResId) {
    ImageView view = retrieveView(viewId);
    view.setImageResource(imageResId);
    return this;
  }

  /**
   * Will set background color of a view.
   *
   * @param viewId The view id.
   * @param color  A color, not a resource id.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setBackgroundColor(int viewId, int color) {
    View view = retrieveView(viewId);
    view.setBackgroundColor(color);
    return this;
  }

  /**
   * Will set background of a view.
   *
   * @param viewId        The view id.
   * @param backgroundRes A resource to use as a background.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setBackgroundRes(int viewId, int backgroundRes) {
    View view = retrieveView(viewId);
    view.setBackgroundResource(backgroundRes);
    return this;
  }

  /**
   * Will set text color of a TextView.
   *
   * @param viewId    The view id.
   * @param textColor The text color (not a resource id).
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setTextColor(int viewId, int textColor) {
    TextView view = retrieveView(viewId);
    view.setTextColor(textColor);
    return this;
  }

  /**
   * Will set text color of a TextView.
   *
   * @param viewId       The view id.
   * @param textColorRes The text color resource id.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setTextColorRes(int viewId, int textColorRes) {
    TextView view = retrieveView(viewId);
    view.setTextColor(ContextCompat.getColor(context, textColorRes));
    return this;
  }

  /**
   * Will set the image of an ImageView from a drawable.
   *
   * @param viewId   The view id.
   * @param drawable The image drawable.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setImageDrawable(int viewId, Drawable drawable) {
    ImageView view = retrieveView(viewId);
    view.setImageDrawable(drawable);
    return this;
  }

  /**
   * Add an action to set the image of an image view. Can be called multiple times.
   */
  public LfpViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
    ImageView view = retrieveView(viewId);
    view.setImageBitmap(bitmap);
    return this;
  }

  /**
   * Set a view visibility to VISIBLE (true) or GONE (false).
   *
   * @param viewId  The view id.
   * @param visible True for VISIBLE, false for GONE.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setVisible(int viewId, boolean visible) {
    View view = retrieveView(viewId);
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
    return this;
  }

  /**
   * Set a view visibility to VISIBLE (true) or GONE (false).
   *
   * @param viewId  The view id.
   * @param visible True for VISIBLE, false for GONE.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setVisibleViewGroup(int viewId, boolean visible) {
    ViewGroup viewGroup = retrieveView(viewId);
    viewGroup.setVisibility(visible ? View.VISIBLE : View.GONE);
    return this;
  }

  /**
   * Add links into a TextView.
   *
   * @param viewId The id of the TextView to linkify.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder linkify(int viewId) {
    TextView view = retrieveView(viewId);
    Linkify.addLinks(view, Linkify.ALL);
    return this;
  }

  /**
   * Apply the typeface to the given viewId, and enable subpixel rendering.
   */
  public LfpViewHolder setTypeface(int viewId, Typeface typeface) {
    TextView view = retrieveView(viewId);
    view.setTypeface(typeface);
    view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    return this;
  }

  /**
   * Apply the typeface to all the given viewIds, and enable subpixel rendering.
   */
  public LfpViewHolder setTypeface(Typeface typeface, int... viewIds) {
    for (int viewId : viewIds) {
      TextView view = retrieveView(viewId);
      view.setTypeface(typeface);
      view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
    return this;
  }

  /**
   * Sets the progress of a ProgressBar.
   *
   * @param viewId   The view id.
   * @param progress The progress.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setProgress(int viewId, int progress) {
    ProgressBar view = retrieveView(viewId);
    view.setProgress(progress);
    return this;
  }

  /**
   * Sets the progress and max of a ProgressBar.
   *
   * @param viewId   The view id.
   * @param progress The progress.
   * @param max      The max value of a ProgressBar.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setProgress(int viewId, int progress, int max) {
    ProgressBar view = retrieveView(viewId);
    view.setMax(max);
    view.setProgress(progress);
    return this;
  }

  /**
   * Sets the range of a ProgressBar to 0...max.
   *
   * @param viewId The view id.
   * @param max    The max value of a ProgressBar.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setMax(int viewId, int max) {
    ProgressBar view = retrieveView(viewId);
    view.setMax(max);
    return this;
  }

  /**
   * Sets the rating (the number of stars filled) of a RatingBar.
   *
   * @param viewId The view id.
   * @param rating The rating.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setRating(int viewId, float rating) {
    RatingBar view = retrieveView(viewId);
    view.setRating(rating);
    return this;
  }

  /**
   * Sets the rating (the number of stars filled) and max of a RatingBar.
   *
   * @param viewId The view id.
   * @param rating The rating.
   * @param max    The range of the RatingBar to 0...max.
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setRating(int viewId, float rating, int max) {
    RatingBar view = retrieveView(viewId);
    view.setMax(max);
    view.setRating(rating);
    return this;
  }

  /**
   * Sets the on click listener of the view.
   *
   * @param viewId   The view id.
   * @param listener The on click listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
    View view = retrieveView(viewId);
    view.setOnClickListener(listener);
    return this;
  }

  /**
   * Sets the on touch listener of the view.
   *
   * @param viewId   The view id.
   * @param listener The on touch listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
    View view = retrieveView(viewId);
    view.setOnTouchListener(listener);
    return this;
  }

  /**
   * Sets the on long click listener of the view.
   *
   * @param viewId   The view id.
   * @param listener The on long click listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
    View view = retrieveView(viewId);
    view.setOnLongClickListener(listener);
    return this;
  }

  /**
   * Sets the listview or gridview's item click listener of the view
   *
   * @param viewId   The view id.
   * @param listener The item on click listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener
      listener) {
    AdapterView view = retrieveView(viewId);
    view.setOnItemClickListener(listener);
    return this;
  }

  /**
   * Sets the listview or gridview's item long click listener of the view
   *
   * @param viewId   The view id.
   * @param listener The item long click listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener
      listener) {
    AdapterView view = retrieveView(viewId);
    view.setOnItemLongClickListener(listener);
    return this;
  }

  /**
   * Sets the listview or gridview's item selected click listener of the view
   *
   * @param viewId   The view id.
   * @param listener The item selected click listener;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setOnItemSelectedClickListener(int viewId, AdapterView
      .OnItemSelectedListener listener) {
    AdapterView view = retrieveView(viewId);
    view.setOnItemSelectedListener(listener);
    return this;
  }

  /**
   * Sets the tag of the view.
   *
   * @param viewId The view id.
   * @param tag    The tag;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setTag(int viewId, Object tag) {
    View view = retrieveView(viewId);
    view.setTag(tag);
    return this;
  }

  /**
   * Sets the tag of the view.
   *
   * @param viewId The view id.
   * @param key    The key of tag;
   * @param tag    The tag;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setTag(int viewId, int key, Object tag) {
    View view = retrieveView(viewId);
    view.setTag(key, tag);
    return this;
  }

  /**
   * Sets the checked status of a checkable.
   *
   * @param viewId  The view id.
   * @param checked The checked status;
   * @return The LfpViewHolder for chaining.
   */
  public LfpViewHolder setChecked(int viewId, boolean checked) {
    Checkable view = retrieveView(viewId);
    view.setChecked(checked);
    return this;
  }

}
