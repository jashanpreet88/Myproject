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
        return bookings.size();
    }

    @Override
    public Object getItem(int position) {
        return bookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        }

        HotelBooking booking = bookings.get(position);

        android.widget.TextView name = convertView.findViewById(R.id.booking_name);
        android.widget.TextView location = convertView.findViewById(R.id.booking_location);
        android.widget.TextView checkInDate = convertView.findViewById(R.id.booking_check_in_date);
        android.widget.TextView checkOutDate = convertView.findViewById(R.id.booking_check_out_date);
        android.widget.TextView pricePerNight = convertView.findViewById(R.id.booking_price_per_night);
        android.widget.ImageView image = convertView.findViewById(R.id.booking_image);

        name.setText(booking.getName());
        location.setText(booking.getLocation());
        checkInDate.setText("Check-in: " + booking.getCheckIn());
        checkOutDate.setText("Check-out: " + booking.getCheckOut());
        pricePerNight.setText("Price per night: " + booking.getPricePerNight());

        com.squareup.picasso.Picasso.get()
                .load(booking.getImageUrl())
                .into(image);

        return convertView;
    }
}
