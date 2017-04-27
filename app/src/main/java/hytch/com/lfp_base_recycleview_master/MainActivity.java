package hytch.com.lfp_base_recycleview_master;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.hytch.lfp_base_recycleview_library.MultiItemTypeAdapter;
import com.hytch.lfp_base_recycleview_library.anim.AnimateHelper;
import com.hytch.lfp_base_recycleview_library.itemdecoration.HorizontalDividerItemDecoration;

import java.util.Random;

import hytch.com.lfp_base_recycleview_master.adapter.StudentAdapter;
import hytch.com.lfp_base_recycleview_master.bean.Student;

public class MainActivity extends AppCompatActivity {

  RecyclerView mRecyclerView;
  SwipeRefreshLayout mSwipeRefreshLayout;

  StudentAdapter mStudentAdapter;
  SparseArray<Student> oStudentSparseArray;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_id);
    mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_id);

    mSwipeRefreshLayout.setColorSchemeResources(
        android.R.color.holo_blue_light, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light);

    oStudentSparseArray = new SparseArray<>();
    for (int i = 0; i < 6; i++) {
      Student student = new Student(i, "n" + i, i);
      oStudentSparseArray.append(i, student);
    }
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            refreshData();
          }
        }, 2000);
      }
    });

    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setItemAnimator(AnimateHelper.animation(AnimateHelper.SCALE_LEFT, 10f));
    mRecyclerView.addItemDecoration(
        new HorizontalDividerItemDecoration.Builder(this)
            .color(Color.CYAN)
            .size(2)
            .margin(4)
            .build());
    mStudentAdapter = new StudentAdapter(this, R.layout.item_recy, oStudentSparseArray);
    mRecyclerView.setAdapter(mStudentAdapter);
    mStudentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
      @Override
      public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        super.onItemClick(view, holder, position);
      }
    });
  }

  private void refreshData() {
    SparseArray<Student> nStudentSparseArray = new SparseArray<>();
    Random random = new Random();
    for (int i = 0; i < 5; i++) {
      Student student;
      if (i == 1) {
        student = new Student(i, "n" + random.nextInt(5), random.nextInt(6));
      } else if (i == 3) {
        student = new Student(i, "n" + random.nextInt(10), random.nextInt(18));
      } else {
        student = oStudentSparseArray.get(i);
        if (student == null) {
          student = new Student(i, "n" + i, i);
        }
      }
      nStudentSparseArray.append(i, student);
    }
    mStudentAdapter.setDateResult(nStudentSparseArray);
    mSwipeRefreshLayout.setRefreshing(false);
  }
}
