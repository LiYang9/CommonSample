package com.ly723.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(Context context, int layoutId, ViewGroup parent) {
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mViews = new SparseArray<>();
        mConvertView.setTag(this);
    }

    public static ViewHolder getHolder(Context context, View convertView, int layoutId, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, layoutId, parent);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
}