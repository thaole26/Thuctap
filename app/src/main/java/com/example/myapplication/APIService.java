package com.example.myapplication;

import com.example.myapplication.Models.Nhanvien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("getAllNhanvien")
    Call<List<Nhanvien>> getNhanvien();

    @POST("register")
    Call<Void> dangkiNhanvien(@Body RegisterRequest registerRequest);

    @POST("login")
    Call<Integer> dangnhap(@Body LoginRequest loginRequest);
}
