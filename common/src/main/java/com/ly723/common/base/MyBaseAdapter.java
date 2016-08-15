package com.ly723.common.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ly723.common.interfaces.IConvertView;
import com.ly723.common.utils.ViewHolder;

import java.util.List;

/**
 * @Description
 * @Author LiYang
 */
public class MyBaseAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private IConvertView mIConvert;

    public MyBaseAdapter(Context context, List<T> data, int layoutId, IConvertView iConvertView) {
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
        mIConvert = iConvertView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext, convertView, mLayoutId, parent);
        mIConvert.convertDataToView(holder, position);
        return holder.getConvertView();
    }
}
