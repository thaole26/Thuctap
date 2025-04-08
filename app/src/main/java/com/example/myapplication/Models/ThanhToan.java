package com.example.myapplication.Models;

public class ThanhToan {

    private final int mathanhtoan;

    private final String mahd;

    private final String ngaythanhtoan;

    private final double sotiendanhtoan;

    private final String phuongthuc;

    private final int manv_thu;

    private final String ghichu_tt;


    public ThanhToan(int mathanhtoan, String mahd, String ngaythanhtoan, double sotiendanhtoan, String phuongthuc, int manv_thu, String ghichu_tt) {

        this.mathanhtoan = mathanhtoan;

        this.mahd = mahd;

        this.ngaythanhtoan = ngaythanhtoan;

        this.sotiendanhtoan = sotiendanhtoan;

        this.phuongthuc = phuongthuc;

        this.manv_thu = manv_thu;

        this.ghichu_tt = ghichu_tt;

    }


    public int getMathanhtoan() {

        return mathanhtoan;

    }


    public String getMahd() {

        return mahd;

    }


    public String getNgaythanhtoan() {

        return ngaythanhtoan;

    }


    public double getSotiendanhtoan() {

        return sotiendanhtoan;

    }


    public String getPhuongthuc() {

        return phuongthuc;

    }


    public int getManvThu() {

        return manv_thu;

    }


    public String getGhichuTt() {

        return ghichu_tt;

    }


}