package com.example.myapplication.Requests;

public class HoaDonRequest {
    private String mahd;
    private String madk;
    private String ky;
    private String tungay;
    private String denngay;
    private int chisodau;
    private int chisocuoi;
    private String ngaylaphd;
    private int tinhtrang;

    public HoaDonRequest(String mahd, String madk, String ky, String tungay, String denngay,
                         int chisodau, int chisocuoi, String ngaylaphd, int tinhtrang) {
        this.mahd = mahd;
        this.madk = madk;
        this.ky = ky;
        this.tungay = tungay;
        this.denngay = denngay;
        this.chisodau = chisodau;
        this.chisocuoi = chisocuoi;
        this.ngaylaphd = ngaylaphd;
        this.tinhtrang = tinhtrang;
    }
}
