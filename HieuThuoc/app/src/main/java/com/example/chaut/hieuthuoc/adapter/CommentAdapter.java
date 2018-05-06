package com.example.chaut.hieuthuoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.chaut.hieuthuoc.R;
import com.example.chaut.hieuthuoc.model.CommentModel;


/**
 * Created by chaut on 4/29/2018.
 */

public class CommentAdapter extends RecyclerView.ViewHolder{
    View mView;
    Context mContext;

    public CommentAdapter(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindFirebase(CommentModel commentModel){

        TextView tieude = itemView.findViewById(R.id.tv_tieude);
        TextView noidung = itemView.findViewById(R.id.tv_noidung);
        RatingBar rbRating = itemView.findViewById(R.id.rb_rating);
        TextView tvDate = itemView.findViewById(R.id.tv_date);

        tieude.setText(commentModel.getTieude());
        noidung.setText(commentModel.getNoidung());
        rbRating.setRating((float) commentModel.getDanhgia());
        tvDate.setText(commentModel.getDate() + "  ");
    }


}
