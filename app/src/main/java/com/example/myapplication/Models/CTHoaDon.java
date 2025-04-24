package com.example.myapplication.Models;

public class CTHoaDon {
    private final int id_cthd;
    private final String mahd;
    private final int id_mucgia;
    private final int dntt_bac;
    private final double dongia_apdung;
    private final double thanhtien_bac;


    public CTHoaDon(int id_cthd, String mahd, int mabac, int dntt_bac, double dongia_apdung, double thanhtien_bac) {

        this.id_cthd = id_cthd;

        this.mahd = mahd;

        this.id_mucgia = mabac;

        this.dntt_bac = dntt_bac;

        this.dongia_apdung = dongia_apdung;

        this.thanhtien_bac = thanhtien_bac;

    }


    public int getIdCthd() {

        return id_cthd;

    }


    public String getMahd() {

        return mahd;

    }


    public int getId_mucgia() {

        return id_mucgia;

    }


    public int getDnttBac() {

        return dntt_bac;

    }


    public double getDongiaApdung() {

        return dongia_apdung;

    }


    public double getThanhtienBac() {

        return thanhtien_bac;

    }


}