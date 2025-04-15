package com.example.myapplication.Models;

public class NhanVien {
    private int manv;
    private String tendangnhap;
    private String matkhau_hash;
    private String hoten;
    private String email;
    private String sdt;
    private int quyen;
    private int trangthai;

    public NhanVien() {
    }

    public NhanVien(int manv, String tendangnhap, String matkhau_hash, String hoten, String email, String sdt, int quyen, int trangthai) {
        this.manv = manv;
        this.tendangnhap = tendangnhap;
        this.matkhau_hash = matkhau_hash;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;
        this.quyen = quyen;
        this.trangthai = trangthai;
    }

    public int getManv() {
        return manv;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public String getMatkhau_hash() {
        return matkhau_hash;
    }

    public String getHoten() {
        return hoten;
    }

    public String getEmail() {
        return email;
    }

    public String getSdt() {
        return sdt;
    }

    public int getQuyen() {
        return quyen;
    }

    public int getTrangthai() {
        return trangthai;
    }
}
