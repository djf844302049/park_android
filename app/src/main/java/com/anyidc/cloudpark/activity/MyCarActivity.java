package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.CarListAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyCarBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class MyCarActivity extends BaseActivity {
    private CarListAdapter adapter;
    private TextView tvRight;
    private List<MyCarBean> cars = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void initData() {
        initTitle("我的车辆");
        RecyclerView rlvCars = findViewById(R.id.rlv_car_list);
        tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("编辑");
        tvRight.setOnClickListener(clickListener);
        adapter = new CarListAdapter(this, cars);
        adapter.setOnItemClickListener((view, position) -> {
            MyCarBean myCarBean = cars.get(position);
            if (myCarBean.getStatus() == 0 || myCarBean.getStatus() == 3) {
                CarConfirmActivity.actionStart(MyCarActivity.this, myCarBean.getId() + "", 0);
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlvCars.setLayoutManager(manager);
        rlvCars.setNestedScrollingEnabled(false);
        rlvCars.setAdapter(adapter);
        findViewById(R.id.btn_add_car).setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvRight.setText("编辑");
        getCarList();
    }

    private void getCarList() {
        getTime(Api.getDefaultService().getUserCars(0)
                , new RxObserver<BaseEntity<List<MyCarBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<MyCarBean>> carBean) {
                        List<MyCarBean> data = carBean.getData();
                        if (data != null) {
                            cars.clear();
                            cars.addAll(data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_car:
                AddCarActivity.actionStart(this, 0);
                break;
            case R.id.tv_right:
                boolean edit = true;
                if ("编辑".equals(tvRight.getText().toString())) {
                    edit = true;
                    tvRight.setText("完成");
                } else if ("完成".equals(tvRight.getText().toString())) {
                    edit = false;
                    tvRight.setText("编辑");
                }
                for (MyCarBean car : cars) {
                    car.setEdit(edit);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
