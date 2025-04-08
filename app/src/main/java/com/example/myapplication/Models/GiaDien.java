package com.example.myapplication.Models;

public class GiaDien {

    private final int mabac;

    private final String tenbac;

    private final int tusokw;

    private final int densokw;

    private final double dongia;

    private final String ngayapdung;


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