package com.example.myapplication;

public class RegisterRequest {
    private String tendangnhap;
    private String hoten;
    private String sdt;
    private String email;
    private String matkhau;

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
