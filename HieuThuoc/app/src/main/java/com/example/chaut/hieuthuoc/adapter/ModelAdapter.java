package com.example.chaut.hieuthuoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaut.hieuthuoc.DeatilActivity;
import com.example.chaut.hieuthuoc.R;
import com.example.chaut.hieuthuoc.SearchActivity;
import com.example.chaut.hieuthuoc.model.Model;

import java.util.List;

/**
 * Created by chaut on 4/26/2018.
 */

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder>{
    private List<Model> modelList;
    private Context context;

    public ModelAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView ten, diachi, chuyenkhoa, bacsi;

        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
             ten = itemView.findViewById(R.id.tv_ten);
             diachi = itemView.findViewById(R.id.tv_diachi);
             chuyenkhoa = itemView.findViewById(R.id.tv_chuyenkhoa);
             bacsi = itemView.findViewById(R.id.tv_bacsi);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }


        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Model model = modelList.get(position);
        holder.ten.setText(model.getTen());
        holder.diachi.setText("Adr: " +model.getDiachi());
        holder.chuyenkhoa.setText(model.getChuyenkhoa());
        holder.bacsi.setText("Dr: " + model.getBacsi());

        holder.ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeatilActivity.class);
                intent.putExtra("SDT", model.getSdt());
                context.startActivity(intent);
            }
        });


//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick) {
//                    Log.d("bacsi", "onClick: " + modelList.get(position).getBacsi());
//                }
//                else
//                Log.d("bacsi", "onClick: " + modelList.get(position).getBacsi());
//
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }




}
