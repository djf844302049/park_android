package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.CarChoiceAdapter;
import com.anyidc.cloudpark.adapter.ParkUnitNumAdapter;
import com.anyidc.cloudpark.adapter.ShareParkUnitAdapter;
import com.anyidc.cloudpark.dialog.SelectUnitParkDialog;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.CenterBean;
import com.anyidc.cloudpark.moduel.MyCarBean;
import com.anyidc.cloudpark.moduel.ParkDetailBean;
import com.anyidc.cloudpark.moduel.ShareParkBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SelectUnitParkActivity extends BaseActivity implements View.OnClickListener {
    private String id;

    private TextView tvTitle, tvAddress, tvDistance, tvTotal, tvRemain;
    private TextView tvFee;
    private TextView tvAppointment;
    private TextView tvIdAuth, tvCarAuth, tvPay;
    private RecyclerView recyclerView;
    private ImageView ivImage;

    private List<ParkDetailBean.UseArrBean> dataList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> freeList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> usingList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> busyList = new ArrayList<>();
    private List<ShareParkBean.FreeArrBean> shareList = new ArrayList<>();
    private List<ShareParkBean.FreeArrBean> sFreeList = new ArrayList<>();
    private List<ShareParkBean.FreeArrBean> sUsingList = new ArrayList<>();
    private List<ShareParkBean.FreeArrBean> sBusyList = new ArrayList<>();

    private ParkUnitNumAdapter parkUnitNumAdapter;
    private ShareParkUnitAdapter shareParkUnitAdapter;

    private SelectUnitParkDialog selectUnitParkDialog;
    private CenterBean bean;
    private String selectUnitId = "";
    private TextView tvNull, tvApointment, tvParked;
    private TextView[] tvArr = new TextView[3];
    private int type;
    private BottomSheetDialog dialog;
    private RecyclerView rlvCars;
    private CarChoiceAdapter adapter;
    private List<MyCarBean> carList = new ArrayList<>();
    private ParkDetailBean.ParkBean parkInfo;
    private ShareParkBean.ParkBean sParkInfo;
    private String carId = "";
    private int parkType;
    private String parkName = "";
    private float appointNum;
    private String shareTime = "";
    private String shareFee = "";
    private String balanceNum = "";

    public static void start(Context context, String id, int type) {
        Intent intent = new Intent(context, SelectUnitParkActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_unit_park;
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        parkType = getIntent().getIntExtra("type", 0);
        initTitle("选择车位");
        tvNull = findViewById(R.id.tv_null);
        tvNull.setSelected(true);
        tvApointment = findViewById(R.id.tv_has_appointment);
        tvParked = findViewById(R.id.tv_has_parked);
        tvArr[0] = tvNull;
        tvArr[1] = tvApointment;
        tvArr[2] = tvParked;
        tvTitle = findViewById(R.id.tv_parking_name);
        tvAddress = findViewById(R.id.tv_parking_address);
        tvDistance = findViewById(R.id.tv_parking_distance);
        tvTotal = findViewById(R.id.tv_unit_total_num);
        tvRemain = findViewById(R.id.tv_unit_remain);
        tvFee = findViewById(R.id.tv_unit_fee);
        tvAppointment = findViewById(R.id.tv_appointment);
        tvIdAuth = findViewById(R.id.tv_id_certification);
        tvCarAuth = findViewById(R.id.tv_car_certification);
        tvPay = findViewById(R.id.tv_recharge_deposit);
        recyclerView = findViewById(R.id.rv_unit_list);
        ivImage = findViewById(R.id.iv_image);
        ivImage.setOnClickListener(this);
        tvAppointment.setOnClickListener(this);
        tvIdAuth.setOnClickListener(this);
        tvCarAuth.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvNull.setOnClickListener(this);
        tvApointment.setOnClickListener(this);
        tvParked.setOnClickListener(this);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_bankcard_picker);
        rlvCars = (RecyclerView) dialog.findViewById(R.id.rlv_bank_card);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText("请选择需要预约的车辆");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvCars.setLayoutManager(manager);
        adapter = new CarChoiceAdapter(carList);
        rlvCars.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            carId = String.valueOf(carList.get(position).getId());
            PayAppointmentActivity.start(this, parkName, selectUnitId, shareTime, appointNum, carId, shareFee, balanceNum);
            dialog.dismiss();
        });
        switch (parkType) {
            case 1:
                getParkDetail();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
                recyclerView.setLayoutManager(gridLayoutManager);
                parkUnitNumAdapter = new ParkUnitNumAdapter(this, dataList);
                recyclerView.setAdapter(parkUnitNumAdapter);
                ivImage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                parkUnitNumAdapter.setOnItemClickListener((view, position) -> {
                    ParkDetailBean.UseArrBean unitInfoBean = dataList.get(position);
                    String feeStr = "首" + parkInfo.getFee().getFirst_time() + "小时" + parkInfo.getFee().getMoney() + "元，之后" + parkInfo.getFee().getSecond_money() + "元/小时";
                    switch (type) {
                        case 0:
                            selectUnitId = unitInfoBean.getUnit_id();
                            updateAppointBtn();
                            break;
                        case 1:
                            showDialog(unitInfoBean.getUnit_id(), "该车位已被预约", "", "", feeStr);
                            break;
                        case 2:
                            showDialog(unitInfoBean.getUnit_id(), "该车位已有车辆驶入", "", "", feeStr);
                            break;
                    }
                });
                break;
            case 2:
                getShareParkDetail();
                ivImage.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                shareParkUnitAdapter = new ShareParkUnitAdapter(this, shareList);
                shareParkUnitAdapter.setOnItemClickListener((view, position) -> {
                    ShareParkBean.FreeArrBean freeArrBean = shareList.get(position);
                    switch (type) {
                        case 0:
                            selectUnitId = freeArrBean.getUnit_id();
                            shareTime = freeArrBean.getShare_time();
                            appointNum = freeArrBean.getAppointment_money();
                            shareFee = "收费标准：共享时段内" + freeArrBean.getHourfee() + "元/小时,超出共享时段后" + freeArrBean.getOverdose() + "元/小时";
                            tvFee.setText(shareFee);
                            updateAppointBtn();
                            break;
                        case 1:
                            showDialog(freeArrBean.getUnit_id(), "该车位已被预约", "", "", String.valueOf(freeArrBean.getHourfee()));
                            break;
                        case 2:
                            showDialog(freeArrBean.getUnit_id(), "该车位已有车辆驶入", "", "", String.valueOf(freeArrBean.getHourfee()));
                            break;
                    }
                });
                recyclerView.setAdapter(shareParkUnitAdapter);
                break;
        }
        getCenterData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PayResultActivity.rechargeDep) {
            getCenterData();
            PayResultActivity.rechargeDep = false;
        }
    }

    private void getCarList() {
        getTime(Api.getDefaultService().getUserCars()
                , new RxObserver<BaseEntity<List<MyCarBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<MyCarBean>> carBean) {
                        if (carBean.getData() != null && carBean.getData().size() > 0) {
                            for (MyCarBean myCarBean : carBean.getData()) {
                                if (myCarBean.getStatus() == 2) {
                                    carList.add(myCarBean);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        if (carList.size() > 0)
                            dialog.show();
                    }
                });
    }

    private void getCenterData() {
        getTime(Api.getDefaultService().center(),
                new RxObserver<BaseEntity<CenterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<CenterBean> centerBean) {
                        bean = centerBean.getData();
                        if (bean.getIsAuth() == 1) {
                            tvIdAuth.setEnabled(false);
                        }
                        if (bean.getCar_auth() == 1) {
                            tvCarAuth.setEnabled(false);
                        }
                        if (bean.getDeposit_flag() == 1) {
                            tvPay.setEnabled(false);
                        }
                        balanceNum = bean.getUser_money();
                        updateAppointBtn();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_appointment:
                if (bean.getIsAuth() != 1) {
                    ToastUtil.showToast("您还未进行身份认证", Toast.LENGTH_SHORT);
                    return;
                } else if (bean.getCar_auth() != 1) {
                    ToastUtil.showToast("您还未进行车辆认证", Toast.LENGTH_SHORT);
                    return;
                } else if (bean.getDeposit_flag() != 1) {
                    ToastUtil.showToast("您还未缴纳押金", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(selectUnitId)) {
                    ToastUtil.showToast("请选择车位", Toast.LENGTH_SHORT);
                    return;
                }
                if (carList.size() > 0) {
                    dialog.show();
                } else {
                    getCarList();
                }
                break;
            case R.id.tv_id_certification:
                startActivityForResult(new Intent(this, IdentityConfirmActivity.class).putExtra("from", 0), 1);
                break;
            case R.id.tv_car_certification:
                startActivity(new Intent(this, MyCarActivity.class));
                break;
            case R.id.tv_recharge_deposit:
                if (bean != null && bean.getDeposit_flag() != 1) {
                    DepositActivity.actionStart(this, bean.getDeposit_money());
                }
                break;
            case R.id.iv_image:
                ScaleImageActivity.actionStart(this, parkInfo.getBig_img());
                break;
            case R.id.tv_null:
                changeTitleBg(0);
                switch (parkType) {
                    case 1:
                        dataList.addAll(freeList);
                        parkUnitNumAdapter.setType(0);
                        parkUnitNumAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        shareList.addAll(sFreeList);
                        shareParkUnitAdapter.setType(0);
                        shareParkUnitAdapter.notifyDataSetChanged();
                        break;
                }
                break;
            case R.id.tv_has_appointment:
                changeTitleBg(1);
                switch (parkType) {
                    case 1:
                        dataList.addAll(busyList);
                        parkUnitNumAdapter.setType(1);
                        parkUnitNumAdapter.setSelectPos(-1);
                        parkUnitNumAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        shareList.addAll(sBusyList);
                        shareParkUnitAdapter.setType(1);
                        shareParkUnitAdapter.setSelectPos(-1);
                        shareParkUnitAdapter.notifyDataSetChanged();
                        break;
                }
                break;
            case R.id.tv_has_parked:
                changeTitleBg(2);
                switch (parkType) {
                    case 1:
                        dataList.addAll(usingList);
                        parkUnitNumAdapter.setType(1);
                        parkUnitNumAdapter.setSelectPos(-1);
                        parkUnitNumAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        shareList.addAll(sUsingList);
                        shareParkUnitAdapter.setType(1);
                        shareParkUnitAdapter.setSelectPos(-1);
                        shareParkUnitAdapter.notifyDataSetChanged();
                        break;
                }
                break;
        }
    }

    private void changeTitleBg(int pos) {
        type = pos;
        selectUnitId = null;
        updateAppointBtn();
        dataList.clear();
        shareList.clear();
        for (int i = 0; i < tvArr.length; i++) {
            if (i == pos) {
                tvArr[i].setSelected(true);
            } else {
                tvArr[i].setSelected(false);
            }
        }
    }

    private void getParkDetail() {
        getTime(Api.getDefaultService().getParkDetail(id)
                , new RxObserver<BaseEntity<ParkDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkDetailBean> parkDetailBean) {
                        ParkDetailBean data = parkDetailBean.getData();
                        parkInfo = data.getPark();
                        parkName = parkInfo.getParking_name();
                        appointNum = parkInfo.getAppointment_money();
                        if (data.getFree_arr() != null) {
                            freeList.clear();
                            freeList.addAll(data.getFree_arr());
                        }
                        if (data.getUse_arr() != null) {
                            usingList.clear();
                            usingList.addAll(data.getUse_arr());
                        }
                        if (data.getBusy_arr() != null) {
                            busyList.clear();
                            busyList.addAll(data.getBusy_arr());
                        }
                        dataList.clear();
                        switch (type) {
                            case 0:
                                dataList.addAll(freeList);
                                break;
                            case 1:
                                dataList.addAll(busyList);
                                break;
                            case 2:
                                dataList.addAll(usingList);
                                break;
                        }
                        parkUnitNumAdapter.setType(type);
                        parkUnitNumAdapter.notifyDataSetChanged();
                        tvTitle.setText(parkInfo.getParking_name());
                        tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " ");
                        tvTotal.setText(String.valueOf(parkInfo.getNum()) + "个车位，");
                        tvRemain.setText(String.valueOf(data.getCount_data().getFree()));
                        StringBuffer fee = new StringBuffer("收费标准：");
                        for (String s : parkInfo.getFee_desc()) {
                            fee.append(s).append("，");
                        }
                        fee.deleteCharAt(fee.lastIndexOf("，"));
                        fee.append("。");
                        tvFee.setText(fee.toString());
                        initRealImage();
                        updateDistance(parkInfo.getLat(), parkInfo.getLng());
                    }
                });
    }

    private void getShareParkDetail() {
        getTime(Api.getDefaultService().getShareParkList(id)
                , new RxObserver<BaseEntity<ShareParkBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ShareParkBean> baseEntity) {
                        ShareParkBean data = baseEntity.getData();
                        sParkInfo = data.getPark();
                        parkName = sParkInfo.getParking_name();
                        if (data.getFree_arr() != null) {
                            sFreeList.clear();
                            sFreeList.addAll(data.getFree_arr());
                        }
                        if (data.getUse_arr() != null) {
                            sUsingList.clear();
                            sUsingList.addAll(data.getUse_arr());
                        }
                        if (data.getBusy_arr() != null) {
                            sBusyList.clear();
                            sBusyList.addAll(data.getBusy_arr());
                        }
                        shareList.clear();
                        switch (type) {
                            case 0:
                                shareList.addAll(sFreeList);
                                break;
                            case 1:
                                shareList.addAll(sBusyList);
                                break;
                            case 2:
                                shareList.addAll(sUsingList);
                                break;
                        }
                        shareParkUnitAdapter.setType(type);
                        shareParkUnitAdapter.notifyDataSetChanged();
                        tvTitle.setText(sParkInfo.getParking_name());
                        tvAddress.setText(sParkInfo.getArea_1() + " " + sParkInfo.getArea_2() + " " + sParkInfo.getArea_3() + " " + sParkInfo.getArea_4() + " ");
                        tvTotal.setText(String.valueOf(sParkInfo.getNum()) + "个车位，");
                        tvRemain.setText(String.valueOf(data.getCount_data().getFree()));
                        updateDistance(sParkInfo.getLat(), sParkInfo.getLng());
                    }
                });
    }

    /**
     * 初始化实景图
     */
    private void initRealImage() {
        String imgStr = parkInfo.getThumb();
        if (!TextUtils.isEmpty(imgStr)) {
            Glide.with(this).load(imgStr).placeholder(R.drawable.img_place_holder).into(ivImage);
        }
    }

    private void updateAppointBtn() {
        if (bean != null && bean.getCar_auth() == 1 && bean.getIsAuth() == 1 && bean.getDeposit_flag() == 1 && !TextUtils.isEmpty(selectUnitId)) {
            tvAppointment.setBackgroundResource(R.drawable.shape_bg_blue);
        } else {
            tvAppointment.setBackgroundResource(R.drawable.shape_bg_gray);
        }
    }

    private void showDialog(String unitNum, String des, String shareTime, String
            appointmentTime, String pFee) {
        if (selectUnitParkDialog == null) {
            selectUnitParkDialog = new SelectUnitParkDialog(SelectUnitParkActivity.this, unitNum, des, shareTime, appointmentTime, pFee);
        } else {
            selectUnitParkDialog.setText(unitNum, des, shareTime, appointmentTime, pFee);
        }
        selectUnitParkDialog.show();
    }

    private void updateDistance(String lat1, String lng1) {
        String lat = (String) SpUtils.get(SpUtils.MYLATITUDE, "");
        String lng = (String) SpUtils.get(SpUtils.MYLONGITUDE, "");
        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) &&
                !TextUtils.isEmpty(lat1) && !TextUtils.isEmpty(lng1)) {
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            LatLng latLng1 = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng1) / 1000;
            DecimalFormat fNum = new DecimalFormat("##0.00");
            String dd = fNum.format(distance);
            tvDistance.setText("距离：" + dd + "km");
        }
    }
}
