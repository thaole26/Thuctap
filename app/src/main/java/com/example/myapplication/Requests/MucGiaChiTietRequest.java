package com.example.myapplication.Requests;

public class MucGiaChiTietRequest {
    private String ten_bac;
    private int tu_kwh;
    private int den_kwh;
    private double don_gia;

    public MucGiaChiTietRequest(String ten_bac, int tu_kwh, int den_kwh, double don_gia) {
        this.ten_bac = ten_bac;
        this.tu_kwh = tu_kwh;
        this.den_kwh = den_kwh;
        this.don_gia = don_gia;
    }

    public String getTen_bac() {
        return ten_bac;
    }

    public int getTu_kwh() {
        return tu_kwh;
    }

    public int getDen_kwh() {
        return den_kwh;
    }

    public double getDon_gia() {
        return don_gia;
    }
}
