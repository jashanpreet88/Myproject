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

    public HotelViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.myproject.R.layout.activity_hotel_data, parent, false);
        return new HotelViewHolder(view);
    }

    class HotelViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private android.widget.TextView nameTextView, locationTextView, ratingTextView;
        private android.widget.ImageView hotelImageView;

        HotelViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            hotelImageView = itemView.findViewById(R.id.hotelImageView);
            itemView.setOnClickListener(v -> onHotelClickListener.onHotelClick(hotelList.get(getAdapterPosition())));
        }

    }

