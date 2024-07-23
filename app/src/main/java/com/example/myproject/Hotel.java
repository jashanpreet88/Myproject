package com.example.myproject;

public class Hotel {
    private String id;
    private String name;
    private String location;
    private double rating;
    private String imageUrl;
    double pricePerNight;
    boolean availablity;
    public Hotel() {
    }

    public Hotel(String name, String location, double rating, String imageUrl,double pricePerNight,boolean availabilty) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.pricePerNight = pricePerNight;
        this.availablity= availabilty;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getPricePerNight() {return pricePerNight;}

    public void setPricePerNight(double pricePerNight) {this.pricePerNight = pricePerNight;}

    public boolean isAvailablity() {return availablity;}

    public void setAvailablity(boolean availablity) {this.availablity = availablity;}
}


