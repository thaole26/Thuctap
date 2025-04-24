package com.example.myapplication;

import com.example.myapplication.Models.BangGiaApDung;
import com.example.myapplication.Models.DienKe;
import com.example.myapplication.Models.HoaDon;
import com.example.myapplication.Models.MucGiaChiTiet;
import com.example.myapplication.Models.KhachHang;
import com.example.myapplication.Requests.DienKeRequest;
import com.example.myapplication.Requests.HoaDonRequest;
import com.example.myapplication.Requests.KhachHangRequest;
import com.example.myapplication.Requests.LoginRequest;
import com.example.myapplication.Requests.RegisterRequest;
import com.example.myapplication.Requests.TinhTienRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<List<MucGiaChiTiet>> getAllGiaDien();

    @GET("/hoadon")
    Call<List<HoaDon>> getAllHoaDon();

    @POST("khachhang")
    Call<ResponseBody> themKhachHang(@Body KhachHangRequest request);

    @PUT("/khachhang/{makh}")
    Call<ResponseBody> suaKhachHang(@Path("makh") String makh, @Body KhachHangRequest request);

    @POST("/dienke")
    Call<ResponseBody> themDienKe(@Body DienKeRequest request);

    @GET("/dienke/{madk}/chisodau")
    Call<Integer> getChiSoDau(@Path("madk") String madk);

    @POST("/tinh-tien")
    Call<Double> tinhtiendien(@Body TinhTienRequest request);

    @POST("/hoadon")
    Call<ResponseBody> taoHoadon(@Body HoaDonRequest request);

    @DELETE("/khachhang/{makh}")
    Call<ResponseBody> xoaKhachHang(@Path("makh") String makh);

    @PUT("/dienke/{madk}")
    Call<ResponseBody> suaDienKe(@Path("madk") String madk, @Body DienKe dienKe);

    @DELETE("/dienke/{madk}")
    Call<ResponseBody> xoaDienKe(@Path("madk") String madk);

    @GET("/banggiaapdung")
    Call<List<BangGiaApDung>> getAllBangGiaApDung();

    @GET("/banggiaapdung/{id}")
    Call<BangGiaApDung> getBangGiaApDungById(@Path("id_banggia") int id_banggia);

    @PUT("/banggiaapdung/{id_banggia}")
    Call<BangGiaApDung> updateBangGiaApDung(@Path("id_banggia") int id, @Body BangGiaApDung banggia);

    @POST("/banggiaapdung")
    Call<BangGiaApDung> insertBangGia(@Body BangGiaApDung banggia);

}
