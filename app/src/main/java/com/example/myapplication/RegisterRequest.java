package com.example.myapplication;

public class RegisterRequest {
    private final String tendangnhap;
    private final String hoten;
    private final String sdt;
    private final String email;
    private final String matkhau;

    public RegisterRequest(String tendangnhap,String hoten, String sdt, String email, String matkhau) {
        this.tendangnhap = tendangnhap;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.matkhau = matkhau;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public String getHoten() {
        return hoten;
    }
}
