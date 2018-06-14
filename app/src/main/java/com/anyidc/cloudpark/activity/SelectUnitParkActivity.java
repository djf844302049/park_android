package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.anyidc.cloudpark.moduel.ShareParkUnitInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.anyidc.cloudpark.utils.ViewUtils;
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
    private LinearLayout llImage;

    private ParkDetailBean.ParkBean parkInfo;
    private List<ParkDetailBean.UseArrBean> dataList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> freeList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> usingList = new ArrayList<>();
    private List<ParkDetailBean.UseArrBean> busyList = new ArrayList<>();

    private ParkUnitNumAdapter parkUnitNumAdapter;
    private ShareParkUnitAdapter shareParkUnitAdapter;

    private SelectUnitParkDialog selectUnitParkDialog;
    private CenterBean bean;
    private String selectUnitId = "";
    private boolean isShare = false;
    private ParkDetailBean.UseArrBean selectParkUnitInfoBean;
    private ShareParkUnitInfo selectShareParkUnitInfo;
    private TextView tvNull, tvApointment, tvParked;
    private TextView[] tvArr = new TextView[3];
    private int type;
    private BottomSheetDialog dialog;
    private RecyclerView rlvCars;
    private CarChoiceAdapter adapter;
    private List<MyCarBean> carList = new ArrayList<>();
    private String carId;
    public static int REQUETCODE = 999;

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, SelectUnitParkActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_unit_park;
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        initTitle("选择车位");
        tvNull = findViewById(R.id.tv_null);
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
        llImage = findViewById(R.id.ll_image);
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
            PayAppointmentActivity.start(this, parkInfo.getParking_name(), selectParkUnitInfoBean.getUnit_id(), "", parkInfo.getAppointment_money(), carId);
            dialog.dismiss();
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        parkUnitNumAdapter = new ParkUnitNumAdapter(this, dataList);
        recyclerView.setAdapter(parkUnitNumAdapter);
        getCenterData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getParkDetial();
    }

    private void getCarList() {
        getTime(Api.getDefaultService().getUserCars()
                , new RxObserver<BaseEntity<List<MyCarBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<MyCarBean>> carBean) {
                        if (carBean.getData() != null && carBean.getData().size() > 0) {
                            for (MyCarBean myCarBean : carBean.getData()) {
//                                if (myCarBean.getStatus() == 2) {
                                carList.add(myCarBean);
//                                }
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
                            return;
                        }
                        if (bean.getDeposit_flag() == 1) {
                            tvPay.setEnabled(false);
                            return;
                        }
                        updateAppointBtn();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_appointment:
//                if (bean.getIsAuth() != 1) {
//                    ToastUtil.showToast("您还未进行身份认证", Toast.LENGTH_SHORT);
//                    return;
//                } else if (bean.getCar_auth() != 1) {
//                    ToastUtil.showToast("您还未进行车辆认证", Toast.LENGTH_SHORT);
//                    return;
//                } else if (bean.getDeposit_flag() != 1) {
//                    ToastUtil.showToast("您还未缴纳押金", Toast.LENGTH_SHORT);
//                    return;
//                }
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
                    startActivity(new Intent(this, DepositActivity.class));
                }
                break;
            case R.id.tv_null:
                selectUnitId = null;
                changeTitleBg(0);
                dataList.addAll(freeList);
                parkUnitNumAdapter.setType(0);
                parkUnitNumAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_has_appointment:
                selectUnitId = null;
                changeTitleBg(1);
                dataList.addAll(busyList);
                parkUnitNumAdapter.setType(1);
                parkUnitNumAdapter.setSelectPos(-1);
                parkUnitNumAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_has_parked:
                selectUnitId = null;
                changeTitleBg(2);
                dataList.addAll(usingList);
                parkUnitNumAdapter.setType(1);
                parkUnitNumAdapter.setSelectPos(-1);
                parkUnitNumAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void changeTitleBg(int pos) {
        type = pos;
        dataList.clear();
        for (int i = 0; i < tvArr.length; i++) {
            if (i == pos) {
                tvArr[i].setTextColor(ContextCompat.getColor(this, R.color.white));
                tvArr[i].setBackgroundColor(ContextCompat.getColor(this, R.color.bg_blue));
            } else {
                tvArr[i].setTextColor(ContextCompat.getColor(this, R.color.bg_blue));
                tvArr[i].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            }
        }
    }

    private void getParkDetial() {
        getTime(Api.getDefaultService().getParkDetail(id)
                , new RxObserver<BaseEntity<ParkDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkDetailBean> parkDetailBean) {
                        ParkDetailBean data = parkDetailBean.getData();
                        parkInfo = data.getPark();
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
                        selectUnitId = null;
                        dataList.clear();
                        dataList.addAll(freeList);
                        updateView();
                        updateDistance();
                    }
                });
    }

    /**
     * 初始化实景图
     */
    private void initRealImage() {
        llImage.removeAllViews();
        String imgStr = parkInfo.getThumb();
        String[] imgArr = imgStr.split(",");
        if (imgArr != null && imgArr.length > 0) {
            for (String imgUrl : imgArr) {
                View view = LayoutInflater.from(this).inflate(R.layout.single_image_layout, null, false);
                ImageView imageView = view.findViewById(R.id.iv_img);
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                if (imgArr.length == 1) {
                    lp.width = ViewUtils.getWindowWidth(this);
                } else {
                    lp.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this, 30);
                }
                lp.height = lp.width * 9 / 16;
                imageView.setLayoutParams(lp);
                Glide.with(this).load(imgUrl).dontAnimate().into(imageView);
                llImage.addView(view);
            }
        }
    }

    private void updateView() {
        if (parkInfo == null) return;
        tvTitle.setText(parkInfo.getParking_name());
        tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " ");
        tvTotal.setText(String.valueOf(parkInfo.getNum()));
        tvFee.setText("收费标准：首" + parkInfo.getFee().getFirst_time() + "小时" + parkInfo.getFee().getMoney() + "元,之后" + parkInfo.getFee().getSecond_money() + "元/小时");
        initRealImage();
        //共享停车场
//        if (parkInfo.getType() == 1) {
//            llImage.setVisibility(View.GONE);
//            if (shareList == null || shareList.size() <= 0) {
//                recyclerView.setVisibility(View.GONE);
//                return;
//            }
//            recyclerView.setVisibility(View.VISIBLE);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            shareParkUnitAdapter = new ShareParkUnitAdapter(this, shareList);
//            shareParkUnitAdapter.setOnItemClickListener(new ItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    ShareParkUnitInfo shareParkUnitInfo = shareList.get(position);
//                    if (shareParkUnitInfo != null && shareParkUnitInfo.getStatus() == 1 && shareParkUnitInfo.getFrozen_time() == 0) {
//                        selectUnitId = shareParkUnitInfo.getUnit_id();
//                        isShare = true;
//                        selectShareParkUnitInfo = shareParkUnitInfo;
//                        showDialog(shareParkUnitInfo.getUnit_id(), "该车位为共享车位，可进行预约", "", "", String.valueOf(shareParkUnitInfo.getFee().getMoney()));
//                        updateAppointBtn();
//                    } else if (shareParkUnitInfo.getStatus() == 2) {
//                        showDialog(shareParkUnitInfo.getUnit_id(), "该车位已有车辆驶入", "", "", String.valueOf(shareParkUnitInfo.getFee().getMoney()));
//                    } else {
//                        showDialog(shareParkUnitInfo.getUnit_id(), "该车位为共享车位且已被预约", "", "", String.valueOf(shareParkUnitInfo.getFee().getMoney()));
//                    }
//                }
//            });
//            recyclerView.setAdapter(shareParkUnitAdapter);
//        } else {
        llImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        parkUnitNumAdapter.setOnItemClickListener((view, position) -> {
            ParkDetailBean.UseArrBean unitInfoBean = dataList.get(position);
            String feeStr = "首" + parkInfo.getFee().getFirst_time() + "小时" + parkInfo.getFee().getMoney() + "元,之后" + parkInfo.getFee().getSecond_money() + "元/小时";
            switch (type) {
                case 0:
                    selectUnitId = unitInfoBean.getUnit_id();
                    isShare = false;
                    selectParkUnitInfoBean = unitInfoBean;
                    break;
                case 1:
                    showDialog(unitInfoBean.getUnit_id(), "该车位已被预约", "", "", feeStr);
                    break;
                case 2:
                    showDialog(unitInfoBean.getUnit_id(), "该车位已有车辆驶入", "", "", feeStr);
                    break;
            }
        });
        parkUnitNumAdapter.setType(0);
        parkUnitNumAdapter.setSelectPos(-1);
        parkUnitNumAdapter.notifyDataSetChanged();
//        }
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

    private void updateDistance() {
        String lat = (String) SpUtils.get(SpUtils.MYLATITUDE, "");
        String lng = (String) SpUtils.get(SpUtils.MYLONGITUDE, "");
        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && parkInfo != null) {
            LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            LatLng latLng1 = new LatLng(Double.parseDouble(parkInfo.getLat()), Double.parseDouble(parkInfo.getLng()));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng1) / 1000;
            DecimalFormat fNum = new DecimalFormat("##0.00");
            String dd = fNum.format(distance);
            tvDistance.setText(dd + "km");
        }
    }
}
