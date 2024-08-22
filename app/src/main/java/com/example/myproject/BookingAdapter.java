package com.example.myproject;

public class BookingAdapter  extends android.widget.BaseAdapter {
    private android.content.Context context;
    private java.util.List<HotelBooking> bookings;

    public BookingAdapter(android.content.Context context, java.util.List<HotelBooking> bookings) {
        this.context = context;
        this.bookings = bookings;
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
