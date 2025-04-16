package com.example.myapplication.Requests;

public class LoginRequest {
    private final String tendangnhap;
    private final String matkhau;

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
