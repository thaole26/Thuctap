package com.example.myapplication;

import com.example.myapplication.Models.DienKe;
import com.example.myapplication.Models.GiaDien;
import com.example.myapplication.Models.KhachHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("register")
    Call<Void> dangkiNhanVien(@Body RegisterRequest registerRequest);

    @POST("login")
    Call<Integer> dangnhap(@Body LoginRequest loginRequest);

    @GET("khachhang")
    Call<List<KhachHang>> getAllKhachHang();

    @GET("dienke")
    Call<List<DienKe>> getAllDienKe();

    @GET("giadien")
    Call<List<GiaDien>> getAllGiaDien();

}
