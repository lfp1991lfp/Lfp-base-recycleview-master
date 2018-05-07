package hytch.com.lfp_base_recycleview_master;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lfp.lfp_base_recycleview_library.MultiItemTypeAdapter;
import com.lfp.lfp_base_recycleview_library.anim.AnimateHelper;
import com.lfp.lfp_base_recycleview_library.itemdecoration.HorizontalDividerItemDecoration;
import com.lfp.lfp_base_recycleview_library.layoutmanager.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hytch.com.lfp_base_recycleview_master.adapter.StudentTestAdapter;
import hytch.com.lfp_base_recycleview_master.bean.Student;
import hytch.com.lfp_base_recycleview_master.snap.LinearSnapActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //    StudentAdapter mStudentAdapter;
    StudentTestAdapter mStudentAdapter;
    List<Student> oStudentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_id);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_id);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        oStudentSparseArray = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Student student = new Student(i, "n" + i, i);
            oStudentSparseArray.add(student);
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

        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mRecyclerView.setItemAnimator(AnimateHelper.animation(AnimateHelper.SCALE_LEFT, 10f));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .colorResId(R.color.no_data_bg)
                        .size(2)
                        .margin(4)
                        .build());
//        mStudentAdapter = new StudentAdapter(this, R.layout.item_recy, oStudentSparseArray);
        mStudentAdapter = new StudentTestAdapter(this, R.layout.item_recy, oStudentSparseArray);
        mRecyclerView.setAdapter(mStudentAdapter);
        mStudentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
            }
        });

        findViewById(R.id.btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LinearSnapActivity.class));
//                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }

    private void refreshData() {
        List<Student> nStudentSparseArray = new ArrayList<>();
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
            nStudentSparseArray.add(student);
        }
//        mStudentAdapter.setDateResult(nStudentSparseArray);
        mStudentAdapter.setDataList(nStudentSparseArray);
        mStudentAdapter.submitList(nStudentSparseArray);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
