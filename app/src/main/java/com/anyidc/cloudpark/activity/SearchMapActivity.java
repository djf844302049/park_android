package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.SearchView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/2/8.
 */

public class SearchMapActivity extends BaseActivity implements View.OnClickListener {

    private SearchView searchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_place;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        searchView = findViewById(R.id.sv_map);
        initSearchView();
    }

    private void initSearchView() {
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

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
}
