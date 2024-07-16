package com.example.myproject;

public class Hotel {
    private String id;
    private String name;
    private String location;
    private double rating;
    private String imageUrl;

    public Hotel() {
        // Empty constructor needed for Firestore
    }

    public Hotel(String name, String location, double rating, String imageUrl) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;
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
}
