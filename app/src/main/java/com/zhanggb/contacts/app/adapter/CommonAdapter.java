package com.zhanggb.contacts.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/15/2016
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater inflater;
    protected Context context;
    protected List<T> datas;
    protected final int itemLayoutId;

    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = datas;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent, itemLayoutId, position);
        convert(viewHolder, datas.get(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T item);
}
