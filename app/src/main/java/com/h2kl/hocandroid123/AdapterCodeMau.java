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

public class AdapterCodeMau extends ArrayAdapter<codemau> {
    Context context;
    int resource;
    ArrayList<codemau> arrayList;

    public AdapterCodeMau(@NonNull Context context, int resource, @NonNull ArrayList<codemau> arrayList) {
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
            holder.txtcodemau = convertView.findViewById(R.id.txtcodemau);
            holder.cardView= convertView.findViewById(R.id.cviewcode);

            convertView.setTag(holder);
        } else {
            holder = (holder) convertView.getTag();
        }
        codemau it = arrayList.get(position);
        holder.txtcodemau.setText(it.tencodemau);
        return convertView;
    }

    public class holder {
        TextView txtcodemau;
        CardView cardView;
    }
}
