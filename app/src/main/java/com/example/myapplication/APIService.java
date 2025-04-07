package com.example.myapplication;

import com.example.myapplication.Models.Nhanvien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("nhanvien")
    Call<List<Nhanvien>> getNhanvien();
}
