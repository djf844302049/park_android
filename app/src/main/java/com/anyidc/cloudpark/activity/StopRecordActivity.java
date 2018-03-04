package com.anyidc.cloudpark.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.StopRecordAdapter;

/**
 * Created by Administrator on 2018/3/4.
 */

public class StopRecordActivity extends BaseActivity{
    private SearchView searchView;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private StopRecordAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_stop_record;
    }

    @Override
    protected void initData() {
        searchView = findViewById(R.id.sv_record);
        initSearchView();
        xRefreshView = findViewById(R.id.my_xrefreshview);
        recyclerView = findViewById(R.id.rlv_stop_record_list);
        adapter = new StopRecordAdapter(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
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
        searchView.setQueryHint(getResources().getString(R.string.search_stop_record));
        searchView.clearFocus();

        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            int searchTextId = searchPlate.getContext().getResources()
                    .getIdentifier("android:id/search_src_text", null, null);
            //文字颜色
            TextView searchText = searchPlate.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextSize(14f);
                searchText.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
                searchText.setHintTextColor(ContextCompat.getColor(this, R.color.text_color_gray));
            }

            //光标颜色
//            try {
//                Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//                mCursorDrawableRes.setAccessible(true);
//                mCursorDrawableRes.set(searchText, R.drawable.cursor_color);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
