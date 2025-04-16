package com.example.myapplication.Requests;

public class DienKeRequest {
    private final String madk;
    private final String makh;
    private final String ngaysx;
    private final String ngaylap;
    private final String diachi_lapdat;
    private final String mota;
    private final int trangthai;

    public DienKeRequest(String madk, String makh, String ngaySX, String ngayLap, String diachi_lapdat, String mota, int trangthai) {
        this.madk = madk;
        this.makh = makh;
        this.ngaysx = ngaySX;
        this.ngaylap = ngayLap;
        this.diachi_lapdat = diachi_lapdat;
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
        return ngaysx;
    }

    public String getNgaylap() {
        return ngaylap;
    }

    public String getDiachi_lapdat() {
        return diachi_lapdat;
    }

    public String getMota() {
        return mota;
    }

    public int getTrangthai() {
        return trangthai;
    }
}

