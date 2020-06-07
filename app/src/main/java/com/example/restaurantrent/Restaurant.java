package com.example.restaurantrent;


import java.io.Serializable;

public class Restaurant implements Serializable {
    private long id;

    private long idOwner;
    private String name;
    private String address;

    public Restaurant() {
    }

    public Restaurant(long idOwner, String name, String address) {
        this.idOwner = idOwner;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(long idOwner) {
        this.idOwner = idOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
