package com.example.myproject;

public class HotelBooking {
    private String id;
    private String name;
    private String location;
    private double rating;
    private String imageUrl;
    private double pricePerNight;
    private boolean available;
    private String checkIn;
    private String checkOut;

    public HotelBooking() {
    }
    public HotelBooking(String name, String location, double rating, String imageUrl, double pricePerNight, boolean available, String checkIn, String checkOut ) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.pricePerNight = pricePerNight;
        this.available = available;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

}
