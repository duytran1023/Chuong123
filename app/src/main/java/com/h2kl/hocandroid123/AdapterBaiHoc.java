package com.h2kl.hocandroid123;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBaiHoc extends ArrayAdapter<baihoc> {
    Context context;
    int resource;
    ArrayList<baihoc> arrayList;
    @ColorInt
    int mauchuong = Color.parseColor("#FFFFFF");
    @ColorInt int maunenchuong = Color.parseColor("#00A65A");
    @ColorInt int maubai = Color.parseColor("#000000");
    @ColorInt int maunenbai = Color.parseColor("#FFFFFF");


    public AdapterBaiHoc(@NonNull Context context, int resource, @NonNull ArrayList<baihoc> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        holder holder;
        if (convertView == null) {
            holder = new holder();
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder.txtbaihoc = convertView.findViewById(R.id.txtbaihoc);
            holder.cardView= convertView.findViewById(R.id.cviewbai);

            convertView.setTag(holder);
        } else {
            holder = (holder) convertView.getTag();
        }
        baihoc it = arrayList.get(position);
        holder.txtbaihoc.setText(it.tenbaihoc);
        if(it.chuong.equals("true")){

            holder.txtbaihoc.setTextSize(21);
            holder.txtbaihoc.setTextColor(mauchuong);
            holder.cardView.setBackgroundColor(maunenchuong);
        }
        else {
            holder.txtbaihoc.setTextSize(20);
            holder.txtbaihoc.setTextColor(maubai);
            holder.cardView.setBackgroundColor(maunenbai);

        }
        return convertView;
    }

    public class holder {
        TextView txtbaihoc;
        CardView cardView;
    }
}
