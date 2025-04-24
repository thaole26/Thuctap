package com.example.myapplication.Models;

import java.util.Date;

public class BangGiaApDung {
    private int id_banggia;
    private String ten_banggia;
    private Date ngay_apdung;
    private Date ngay_ketthuc;
    private byte trangthai;
    private String mota;

    public BangGiaApDung() {
    }

    public int getId_banggia() {
        return id_banggia;
    }

    public void setId_banggia(int id_banggia) {
        this.id_banggia = id_banggia;
    }

    public String getTen_banggia() {
        return ten_banggia;
    }

    public void setTen_banggia(String ten_banggia) {
        this.ten_banggia = ten_banggia;
    }

    public Date getNgay_apdung() {
        return ngay_apdung;
    }

    public void setNgay_apdung(Date ngay_apdung) {
        this.ngay_apdung = ngay_apdung;
    }

    public Date getNgay_ketthuc() {
        return ngay_ketthuc;
    }

    public void setNgay_ketthuc(Date ngay_ketthuc) {
        this.ngay_ketthuc = ngay_ketthuc;
    }

    public byte getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(byte trangthai) {
        this.trangthai = trangthai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}