package com.example.myapplication.Models;

public class HoaDon {

    private final String mahd;
    private final String madk;
    private final String ky;
    private final String tungay;
    private final String denngay;
    private final int chisodau;
    private final int chisocuoi;
    private final double tongthanhtien;
    private final String ngaylapHD;
    private final int tinhtrang;
    private final String ghichu;
    private final int manv_ghi;


    public HoaDon(String mahd, String madk, String ky, String tungay, String denngay, int chisodau, int chisocuoi, double tongthanhtien, String ngaylapHD, int tinhtrang, String ghichu, int manv_ghi) {

        this.mahd = mahd;

        this.madk = madk;

        this.ky = ky;

        this.tungay = tungay;

        this.denngay = denngay;

        this.chisodau = chisodau;

        this.chisocuoi = chisocuoi;

        this.tongthanhtien = tongthanhtien;

        this.ngaylapHD = ngaylapHD;

        this.tinhtrang = tinhtrang;

        this.ghichu = ghichu;

        this.manv_ghi = manv_ghi;

    }


    public String getMahd() {

        return mahd;

    }


    public String getMadk() {

        return madk;

    }


    public String getKy() {

        return ky;

    }


    public String getTungay() {

        return tungay;

    }


    public String getDenngay() {

        return denngay;

    }


    public int getChisodau() {

        return chisodau;

    }


    public int getChisocuoi() {

        return chisocuoi;

    }


    public double getTongthanhtien() {

        return tongthanhtien;

    }


    public String getNgaylaphd() {

        return ngaylapHD;

    }


    public int getTinhtrang() {

        return tinhtrang;

    }


    public String getGhichu() {

        return ghichu;

    }


    public int getManvGhi() {

        return manv_ghi;

    }


}