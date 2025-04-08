package com.example.myapplication;

public class LoginRequest {
    private String tendangnhap;
    private String matkhau;

    public LoginRequest(String tendangnhap, String matkhau) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }
}
