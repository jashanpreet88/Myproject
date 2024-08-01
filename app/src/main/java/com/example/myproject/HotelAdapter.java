package com.example.myproject;

public class HotelAdapter extends android.widget.BaseAdapter {

    private android.content.Context context;
    private java.util.List<Hotel>hotels;
    private com.google.firebase.database.DatabaseReference HotelsDB;

    public  HotelAdapter(android.content.Context context, java.util.List<Hotel>hotels){
        this.context=context;
        this.hotels=hotels;
        HotelsDB = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("hotels");
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
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {

        if (view == null) {
            view = android.view.LayoutInflater.from(context).inflate(R.layout.hotel_item, viewGroup, false);
        }
        final Hotel hotel = hotels.get(i);
        android.widget.ImageView imageView = view.findViewById(R.id.hotel_image);
        android.widget.TextView textView = view.findViewById(R.id.hotel_name);
        com.squareup.picasso.Picasso.get()
                .load(hotel.getImageUrl())
                .into(imageView);
        textView.setText(hotel.getName());

        return view;
    }
}
