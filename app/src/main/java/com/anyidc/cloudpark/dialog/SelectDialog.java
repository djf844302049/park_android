package com.anyidc.cloudpark.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.SelectDialogAdapter;

/**
 * Created by wangzhengshan on 2016/12/16.
 */

public class SelectDialog extends BaseDialog implements AdapterView.OnItemClickListener {
    private Context context;
    private TextView tvTitle;
    private ListView listView;
    private String title = "";
    private String[] items;
    private SelectDialogAdapter adapter;
    private OnItemSelectListener listener;

    public SelectDialog(Context context) {
        super(context);
        this.context = context;
        this.setCanceledOnTouchOutside(true);//设置点击Dialog框空白处自动消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.select_layout, null, false);
        setContentView(view);
        tvTitle = (TextView) view.findViewById(R.id.tv_select_title);
        listView = (ListView) view.findViewById(R.id.lv_select_list);
        tvTitle.setText(title);
        adapter = new SelectDialogAdapter(context);
        adapter.updateList(items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem(String[] items) {
        this.items = items;
        if(adapter != null) {
            adapter.updateList(items);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) adapter.getItem(position);
        if (listener != null) {
            listener.itemSelectListener(position, item);
        }
        dismiss();
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectListener {
        public void itemSelectListener(int position, String name);
    }
}
