package com.example.nhom14_appbansachtrennentangandroid.model;

import java.util.List;

public class ThongBao {
    String idTB, idDH,trangThai,ngayThongBao;

    public ThongBao(String idTB, String idDH, String trangThai, String ngayThongBao) {
        this.idTB=idTB;
        this.idDH = idDH;
        this.trangThai = trangThai;
        this.ngayThongBao = ngayThongBao;
    }

    public String getIdTB() {
        return idTB;
    }

    public void setIdTB(String idTB) {
        this.idTB = idTB;
    }

    public String getIdDH() {
        return idDH;
    }

    public void setIdDH(String idDH) {
        this.idDH = idDH;
    }

    public ThongBao() {
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayThongBao() {
        return ngayThongBao;
    }

    public void setNgayThongBao(String ngayThongBao) {
        this.ngayThongBao = ngayThongBao;
    }
}
