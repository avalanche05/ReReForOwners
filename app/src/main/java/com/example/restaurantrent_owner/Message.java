package com.example.restaurantrent_owner;


public class Message {

    private long id;
    private long idRent;
    // является ли оправитель владельцем
    private boolean owner;
    private String textMessage;

    public Message() {
    }

    public Message(long idRent, Boolean isOwner, String textMessage) {
        this.idRent = idRent;
        this.owner = isOwner;
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

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
