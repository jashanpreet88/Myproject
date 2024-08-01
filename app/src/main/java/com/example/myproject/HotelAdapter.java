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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        return null;
    }
}
