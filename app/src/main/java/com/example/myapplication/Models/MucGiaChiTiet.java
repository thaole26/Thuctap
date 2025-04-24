package com.example.myapplication.Models;

public class MucGiaChiTiet {
    private final int id_mucgia;
    private final int id_banggia;
    private final int bac;
    private final String ten_bac;
    private final int tu_kwh;
    private final int den_kwh;
    private final double don_gia;

    public MucGiaChiTiet(int id_mucgia, int id_banggia, int bac, String ten_bac, int tu_kwh, int den_kwh, double don_gia) {
        this.id_mucgia = id_mucgia;
        this.id_banggia = id_banggia;
        this.bac = bac;
        this.ten_bac = ten_bac;
        this.tu_kwh = tu_kwh;
        this.den_kwh = den_kwh;
        this.don_gia = don_gia;
    }

    public int getId_mucgia() {
        return id_mucgia;
    }

    public int getId_banggia() {
        return id_banggia;
    }

    public int getBac() {
        return bac;
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