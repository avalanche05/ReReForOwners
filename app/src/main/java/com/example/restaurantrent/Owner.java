package com.example.restaurantrent;

public class Owner {
    private long id;
    private String email;
    private String password;
    private int restaurantCount;

    public Owner(long id, String email, String password, int restaurantCount) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.restaurantCount = restaurantCount;
    }

    public Owner(String email, String password, int restaurantCount) {
        this.email = email;
        this.password = password;
        this.restaurantCount = restaurantCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRestaurantCount() {
        return restaurantCount;
    }

    public void setRestaurantCount(int restaurantCount) {
        this.restaurantCount = restaurantCount;
    }
}
