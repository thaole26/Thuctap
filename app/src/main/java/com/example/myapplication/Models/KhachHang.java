package com.example.myapplication.Models;

public class KhachHang {
    private final String makh;
    private final String tenkh;
    private final String diachi;
    private final String dt;
    private final String cmnd;


    public KhachHang(String makh, String tenkh, String diachi, String dt, String cmnd) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.dt = dt;
        this.cmnd = cmnd;
    }

    public String getMakh() {
        return makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getDt() {
        return dt;
    }

    public String getCmnd() {
        return cmnd;
    }
}