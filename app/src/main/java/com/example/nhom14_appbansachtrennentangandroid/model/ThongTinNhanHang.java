package com.example.nhom14_appbansachtrennentangandroid.model;

public class ThongTinNhanHang {
    String diachi;
    String id_User;
    String ngaysinh;
    String sdt;
    String username;

    public ThongTinNhanHang(String diachi, String id_User, String ngaysinh, String sdt, String username) {
        this.diachi = diachi;
        this.id_User = id_User;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.username = username;
    }

    public ThongTinNhanHang() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
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
