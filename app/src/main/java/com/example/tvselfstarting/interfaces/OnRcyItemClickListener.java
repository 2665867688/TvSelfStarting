package com.example.tvselfstarting.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 中国 on 2017/11/11.
 * recyclerveiw回掉接口
 */

public interface OnRcyItemClickListener<T> {
    void onItemClick(RecyclerView recyclerView, T data, int position);
}
