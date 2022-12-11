package com.example.nhom14_appbansachtrennentangandroid.model;

import java.util.List;

public class DonHang {
    String id;
    ThongTinNhanHang thongTinNhanHang;
    List<GioHang> gioHangList;
    long tongTien, tongTienHang;
    String ngayTao;
    String trangThai;

    public DonHang(String id, ThongTinNhanHang thongTinNhanHang, List<GioHang> gioHangList, long tongTien, long tongTienHang, String ngayTao, String trangThai) {
        this.id = id;
        this.thongTinNhanHang = thongTinNhanHang;
        this.gioHangList = gioHangList;
        this.tongTien = tongTien;
        this.tongTienHang = tongTienHang;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public DonHang() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ThongTinNhanHang getThongTinNhanHang() {
        return thongTinNhanHang;
    }

    public void setThongTinNhanHang(ThongTinNhanHang thongTinNhanHang) {
        this.thongTinNhanHang = thongTinNhanHang;
    }

    public List<GioHang> getGioHangList() {
        return gioHangList;
    }

    public void setGioHangList(List<GioHang> gioHangList) {
        this.gioHangList = gioHangList;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public long getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(long tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
