package com.example.nhom14_appbansachtrennentangandroid.model;

public class Chat {
    String id_User;
    String id_chat;
    String noidung;
    String thoigian;

    public Chat(String id_User, String id_chat, String noidung, String thoigian) {
        this.id_User = id_User;
        this.id_chat = id_chat;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public Chat() {
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
}
