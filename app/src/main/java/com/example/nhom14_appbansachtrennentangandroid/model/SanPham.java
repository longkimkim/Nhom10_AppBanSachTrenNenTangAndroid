package com.example.nhom14_appbansachtrennentangandroid.model;

public class SanPham {
    String idSp;
    String nxb;
    int dongia;
    String img;
    String maDanhMuc;
    String moTa;
    float saoDanhGia;
    int slCon;
    String tenSP;
    String tenTacGia;

    public SanPham(String idSp, String nxb, int donGia, String img, String maDanhMuc, String moTa, float saoDanhGia, int slCon, String tenSP, String tenTacGia) {
        this.idSp = idSp;
        this.nxb = nxb;
        this.dongia = donGia;
        this.img = img;
        this.maDanhMuc = maDanhMuc;
        this.moTa = moTa;
        this.saoDanhGia = saoDanhGia;
        this.slCon = slCon;
        this.tenSP = tenSP;
        this.tenTacGia = tenTacGia;
    }

    public SanPham() {
    }

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }


    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }
    public int getDonGia() {
        return dongia;
    }

    public void setDonGia(int donGia) {
        this.dongia = donGia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getSaoDanhGia() {
        return saoDanhGia;
    }

    public void setSaoDanhGia(float saoDangGia) {
        this.saoDanhGia = saoDangGia;
    }

    public int getSlCon() {
        return slCon;
    }

    public void setSlCon(int slCon) {
        this.slCon = slCon;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
}
