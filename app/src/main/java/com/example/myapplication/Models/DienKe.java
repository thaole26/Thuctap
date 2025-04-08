package com.example.myapplication.Models;

public class DienKe {

    private final String madk;

    private final String makh;

    private final String ngaySX;

    private final String ngayLap;

    private final String mota;

    private final int trangthai;


    public DienKe(String madk, String makh, String ngaySX, String ngayLap, String mota, int trangthai) {

        this.madk = madk;

        this.makh = makh;

        this.ngaySX = ngaySX;

        this.ngayLap = ngayLap;

        this.mota = mota;

        this.trangthai = trangthai;

    }


    public String getMadk() {

        return madk;

    }


    public String getMakh() {

        return makh;

    }


    public String getNgaysx() {

        return ngaySX;

    }


    public String getNgaylap() {

        return ngayLap;

    }


    public String getMota() {

        return mota;

    }


    public int getTrangthai() {

        return trangthai;

    }


}