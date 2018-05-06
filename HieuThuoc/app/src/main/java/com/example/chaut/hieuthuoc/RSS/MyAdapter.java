package com.example.chaut.hieuthuoc.RSS;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chaut.hieuthuoc.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chaut on 3/27/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private static final String TAG = "link";
    public static final String LINK = "LINK";
    Context c;
    ArrayList<Article> articles;

    public MyAdapter(Context c, ArrayList<Article> articles) {
        this.c = c;
        this.articles = articles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article=articles.get(position);

        String title=article.getTitle();
        final String desc=article.getDescription();
        final String link = article.getLink();
        String date=article.getDate();
//        String imageUrl=article.getImageUrl().replace("localhost","10.0.2.2");

        holder.titleTxt.setText(title);
//        holder.desctxt.setText(desc.substring(0,130));
        holder.dateTxt.setText(date);
        //PicassoClient.downloadImage(c,imageUrl,holder.img);
//        Picasso.with(c).load(imageUrl).into(holder.img);
        Picasso.with(c).load(R.drawable.health96).into(holder.img);

        holder.titleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + link);
                Intent intent = new Intent(c, WebViewActivity.class);
                intent.putExtra(LINK, link);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
