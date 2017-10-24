package hytch.com.lfp_base_recycleview_master.adapter;

import android.content.Context;

import com.hytch.lfp_base_recycleview_library.CommonAdapter;
import com.hytch.lfp_base_recycleview_library.base.LfpViewHolder;

import java.util.List;

import hytch.com.lfp_base_recycleview_master.R;
import hytch.com.lfp_base_recycleview_master.bean.Student;

/**
 * Created by lfp on 16/10/10.
 * 学生适配器
 */

public class StudentAdapter extends CommonAdapter<Student> {

  public StudentAdapter(Context context, int layoutId, List<Student> studentSparseArray) {
    super(context, layoutId, studentSparseArray);
  }

  @Override
  protected void convert(LfpViewHolder holder, Student item, int position) {
    holder.setText(R.id.name_txt, item.getName());
    holder.setText(R.id.age_txt, "" + item.getAge());
  }

  @Override
  protected boolean areItemsTheSame(Student item1, Student item2) {
    return item1.getId() == item2.getId();
  }

  @Override
  protected boolean areContentsTheSame(Student item1, Student item2) {
    return item1.getName().equals(item2.getName());
  }

  @Override
  protected Student getChangePayload(List<Student> oldList, int oldItemPosition,
                                     List<Student> newList, int newItemPosition) {
    if (!oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName())) {
      return newList.get(newItemPosition);
    }
    return super.getChangePayload(oldList, oldItemPosition, newList, newItemPosition);
  }
}
