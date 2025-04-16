package com.example.myapplication.Requests;

public class KhachHangRequest {
    private final String makh;
    private final String tenkh;
    private final String dt;
    private final String diachi;
    private final String cmnd;

    public KhachHangRequest(String makh, String tenkh, String dt, String diachi, String cmnd) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.dt = dt;
        this.diachi = diachi;
        this.cmnd = cmnd;
    }

    public String getMakh() {
        return makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public String getDt() {
        return dt;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getCmnd() {
        return cmnd;
    }
}
