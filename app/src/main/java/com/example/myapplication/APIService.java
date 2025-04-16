package com.example.myapplication;

import com.example.myapplication.Models.DienKe;
import com.example.myapplication.Models.GiaDien;
import com.example.myapplication.Models.KhachHang;
import com.example.myapplication.Requests.DienKeRequest;
import com.example.myapplication.Requests.KhachHangRequest;
import com.example.myapplication.Requests.LoginRequest;
import com.example.myapplication.Requests.RegisterRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @POST("khachhang")
    Call<ResponseBody> themKhachHang(@Body KhachHangRequest request);

    @PUT("/khachhang/{makh}")
    Call<ResponseBody> suaKhachHang(@Path("makh") String makh, @Body KhachHangRequest request);

    @POST("/dienke")
    Call<ResponseBody> themDienKe(@Body DienKeRequest request);

}
