package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminHotelAdapter extends BaseAdapter {
    private Context context;
    private List<Hotel> hotels;
    private DatabaseReference HotelsDB;

    public AdminHotelAdapter(Context context, List<Hotel> hotels) {
        this.context = context;
        this.hotels = hotels;
        HotelsDB = FirebaseDatabase.getInstance().getReference("hotels");
    }
    @Override
    public int getCount() {
        return hotels.size();
    }

    @Override
    public Object getItem(int i) {
        return hotels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.admin_hotel_item, viewGroup, false);
        }
        final Hotel hotel = hotels.get(i);
        ImageView imageView = view.findViewById(R.id.hotel_image);
        TextView textView = view.findViewById(R.id.hotel_name);
        Picasso.get()
                .load(hotel.getImageUrl())
                .into(imageView);
        textView.setText(hotel.getName());
        Button btn_view = view.findViewById(R.id.btn_view);
        Button btn_edit = view.findViewById(R.id.btn_edit);
        Button btn_delete = view.findViewById(R.id.btn_delete);


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewHotelActivity.class);
                intent.putExtra("hotel_id", hotel.getId());
                context.startActivity(intent);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditHotelActivity.class);
                intent.putExtra("hotel_id", hotel.getId());
                context.startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(context,DeleteHotelActivity.class);
                intent.putExtra("hotel_id", hotel.getId());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
