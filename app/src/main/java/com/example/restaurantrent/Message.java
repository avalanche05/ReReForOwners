package com.example.restaurantrent;


public class Message {

    private long id;
    private long idRent;
    private Boolean isOwner;
    private String textMessage;

    public Message() {
    }

    public Message(long idRent, Boolean isOwner, String textMessage) {
        this.idRent = idRent;
        this.isOwner = isOwner;
        this.textMessage = textMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRent() {
        return idRent;
    }

    public void setIdRent(long idRent) {
        this.idRent = idRent;
    }

    public Boolean isOwner() {
        return true;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
