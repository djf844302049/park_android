package com.anyidc.cloudpark.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.amap.api.maps.MapView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.AreaAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.HotAreaBean;
import com.anyidc.cloudpark.moduel.ParkSearchBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.wiget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class SearchMapActivity extends BaseActivity implements View.OnClickListener {

    private SearchView searchView;
    private RecyclerView rlvHotArea;
    private RecyclerView rlvHistory;
    private List<String> hotAreaList = new ArrayList<>();
    private List<String> searchList = new ArrayList<>();
    private AreaAdapter hotAreaAdapter;
    private AreaAdapter searchAdapter;
    private MapView mapView;
    private LinearLayout llSearch;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_place;
    }

    @Override
    protected void initData() {
        searchView = findViewById(R.id.sv_map);
        initSearchView();
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_clear_history).setOnClickListener(this);
        llSearch = findViewById(R.id.ll_search);
        mapView = findViewById(R.id.map_view);
        rlvHotArea = findViewById(R.id.rlv_hot_area);
        RecyclerView.LayoutManager layoutManager = new FlowLayoutManager();
        rlvHotArea.setLayoutManager(layoutManager);
        rlvHotArea.setNestedScrollingEnabled(false);
        hotAreaAdapter = new AreaAdapter(this, hotAreaList);
        rlvHotArea.setAdapter(hotAreaAdapter);
        hotAreaAdapter.setOnItemClickListener((view, position) -> search(hotAreaList.get(position)));
        hotAreaAdapter.notifyDataSetChanged();
        rlvHistory = findViewById(R.id.rlv_search_history);
        RecyclerView.LayoutManager layoutManager2 = new FlowLayoutManager();
        rlvHistory.setLayoutManager(layoutManager2);
        rlvHistory.setNestedScrollingEnabled(false);
        List<String> historyList = SpUtils.getObject(SpUtils.SEARCHLIST, List.class);
        if (historyList != null) {
            searchList.addAll(historyList);
        }
        searchAdapter = new AreaAdapter(this, searchList);
        rlvHistory.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener((view, position) -> search(searchList.get(position)));
        getHotArea();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                break;
            case R.id.tv_clear_history:
                searchList.clear();
                searchAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initSearchView() {
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                search(query);
                if (!searchList.contains(query)) {
                    searchList.add(query);
                    searchAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint(getResources().getString(R.string.search_place));
        searchView.clearFocus();
    }

    private void getHotArea() {
        getTime(Api.getDefaultService().getHotSearch()
                , new RxObserver<BaseEntity<List<HotAreaBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<HotAreaBean>> hotAreaBean) {
                        List<HotAreaBean> data = hotAreaBean.getData();
                        for (HotAreaBean datum : data) {
                            hotAreaList.add(datum.getSearch_name());
                        }
                        hotAreaAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void search(String target) {
        getTime(Api.getDefaultService().parkingSearch(10, page, target)
                , new RxObserver<BaseEntity<ParkSearchBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkSearchBean> parkSearchBean) {
//                        llSearch.setVisibility(View.GONE);
//                        mapView.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        SpUtils.setObject(SpUtils.SEARCHLIST, searchList);
        super.onDestroy();
    }
}
