package com.ly723.common.interfaces;

import com.ly723.common.utils.ViewHolder;

/**
 * @Description 绑定数据到视图
 * @Author LiYang
 */
public interface IConvertView {
    /**
     * 绑定数据到要显示的视图控件
     */
    void convertDataToView(ViewHolder holder, int position);
}
