package com.example.authenticatorapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListView extends ArrayAdapter<String> {

    private String[] name;
    private Integer[] img;
    private Activity context;
    public CustomListView(Activity context,String[] name,Integer[] img) {
        super(context, R.layout.imagelayout,name);
        this.context=context;
        this.name=name;
        this.img=img;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r=convertView;
        ViewHolder viewHolder = null;
        if (r==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.imagelayout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) r.getTag();
        }

        viewHolder.imageView.setImageResource(img[position]);
        viewHolder.textView.setText(name[position]);

        return r;


    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
        ViewHolder(View view){
            textView= (TextView) view.findViewById(R.id.name);
            imageView= (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
