package com.example.myapplication.Models;

public class DienKe {
    private String madk;
    private String makh;
    private String ngaysx;
    private String ngaylap;
    private String mota;
    private int trangthai;

    public DienKe(String madk, String makh, String ngaysx, String ngaylap, String mota, int trangthai) {
        this.madk = madk;
        this.makh = makh;
        this.ngaysx = ngaysx;
        this.ngaylap = ngaylap;
        this.mota = mota;
        this.trangthai = trangthai;
    }

    public DienKe() {
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

    public String getMota() {
        return mota;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setMadk(String madk) {
        this.madk = madk;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public void setNgaysx(String ngaysx) {
        this.ngaysx = ngaysx;
    }

    public void setNgaylap(String ngaylap) {
        this.ngaylap = ngaylap;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
