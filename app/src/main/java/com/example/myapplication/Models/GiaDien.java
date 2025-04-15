package com.example.myapplication.Models;

public class GiaDien {
    private int mabac;
    private String tenbac;
    private int tusokw;
    private int densokw;
    private double dongia;
    private String ngayapdung;


    public GiaDien(int mabac, String tenbac, int tusokw, int densokw, double dongia, String ngayapdung) {

        this.mabac = mabac;

        this.tenbac = tenbac;

        this.tusokw = tusokw;

        this.densokw = densokw;

        this.dongia = dongia;

        this.ngayapdung = ngayapdung;

    }


    public int getMabac() {

        return mabac;

    }


    public String getTenbac() {

        return tenbac;

    }


    public int getTusokw() {

        return tusokw;

    }


    public int getDensokw() {

        return densokw;

    }


    public double getDongia() {

        return dongia;

    }


    public String getNgayapdung() {

        return ngayapdung;

    }


}