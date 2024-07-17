package com.example.myproject;

public class HotelAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private java.util.List<Hotel> hotelList;
    private OnHotelClickListener onHotelClickListener;

    public HotelAdapter(java.util.List<Hotel> hotelList, OnHotelClickListener onHotelClickListener) {
        this.hotelList = hotelList;
        this.onHotelClickListener = onHotelClickListener;
    }

    interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }
}
