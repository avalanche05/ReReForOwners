package com.example.restaurantrent_owner;

import java.io.Serializable;

public class Board implements Serializable {
    private long id;

    private long idRestaurant;
    private float x;
    private float y;

    public Board() {
    }

    public Board(long idRestaurant, float x, float y) {
        this.idRestaurant = idRestaurant;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
