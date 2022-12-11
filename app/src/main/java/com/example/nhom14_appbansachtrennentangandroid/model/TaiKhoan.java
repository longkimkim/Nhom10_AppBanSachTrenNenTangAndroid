package com.example.nhom14_appbansachtrennentangandroid.model;

import java.util.Date;

public class TaiKhoan {
    String avt;
    String diachi;
    String email;
    String gioitinh;
    String id_User;
    String ngaysinh;
    String sdt;
    String username;

    public TaiKhoan(String avt, String diachi, String email, String gioitinh, String id_User, String ngaysinh, String sdt, String username) {
        this.avt = avt;
        this.diachi = diachi;
        this.email = email;
        this.gioitinh = gioitinh;
        this.id_User = id_User;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.username = username;
    }

    public TaiKhoan() {
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
