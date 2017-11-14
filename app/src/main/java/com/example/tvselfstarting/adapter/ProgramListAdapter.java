package com.example.tvselfstarting.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvselfstarting.R;
import com.example.tvselfstarting.domain.ProgramInfo;
import com.example.tvselfstarting.interfaces.OnRcyItemClickListener;

import java.util.List;

/**
 * Created by 中国 on 2017/11/11.
 */

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.MyViewHolder> {

    private List<ProgramInfo> list;

    public ProgramListAdapter(List<ProgramInfo> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program_list, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProgramInfo programInfo = list.get(position);
        holder.tvName.setText("程序名：" + programInfo.getName());
        if (programInfo.getIcon()!=null) {
            holder.imgIcon.setImageDrawable(programInfo.getIcon());
        }
        holder.tvPackageName.setText("程序包名：" + programInfo.getPackageName());
        holder.tvLauchActivity.setText("首启动Activity：" + programInfo.getFirstActivityName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRcyItemClickListener.onItemClick((RecyclerView) holder.itemView.getParent(), programInfo, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPackageName;
        TextView tvLauchActivity;
        ImageView imgIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_program_name);
            tvLauchActivity = itemView.findViewById(R.id.tv_program_lauchactivity);
            imgIcon = itemView.findViewById(R.id.img_program);
            tvPackageName = itemView.findViewById(R.id.tv_program_packagename);
        }
    }


    private OnRcyItemClickListener onRcyItemClickListener;

    public void setOnRcyItemClickListener(OnRcyItemClickListener onRcyItemClickListener) {
        this.onRcyItemClickListener = onRcyItemClickListener;
    }
}

