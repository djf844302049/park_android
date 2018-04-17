package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by wangzhengshan on 2016/12/16.
 */

public class SelectDialogAdapter extends BaseAdapter {
    private String[] contents;
    private Context context;
    private LayoutInflater mInflater;

    public SelectDialogAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(String[] contents) {
        this.contents = contents;
    }

    @Override
    public int getCount() {
        return contents == null ? 0 : contents.length;
    }

    @Override
    public Object getItem(int position) {
        return contents[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.single_textview, null, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_select_item);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String text = (String) getItem(position);
        if (!TextUtils.isEmpty(text)) {
            viewHolder.tv.setText(text);
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView tv;
    }
}