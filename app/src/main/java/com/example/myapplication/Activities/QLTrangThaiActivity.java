package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIService;
import com.example.myapplication.Adapters.BangGiaApDungAdapter;
import com.example.myapplication.Models.BangGiaApDung;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLTrangThaiActivity extends AppCompatActivity implements BangGiaApDungAdapter.OnItemClickListener {

    TextView textViewDanhSachLabel;
    Spinner spinnerChonNamXem;
    Button buttonXemGiaTheoNam;
    RecyclerView recyclerViewDanhSachGia;
    Button buttonThemBangGiaMoi;
    Button buttonSuaBangGia;

    List<BangGiaApDung> listBangGia;
    BangGiaApDungAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlybangtrangthai);

        addControls();
        loadDanhSachBangGia();
        setupSpinnerNam();
        addEvents();
    }

    private void addControls() {
        textViewDanhSachLabel = findViewById(R.id.textViewDanhSachLabel);
        spinnerChonNamXem = findViewById(R.id.spinnerChonNamXem);
        buttonXemGiaTheoNam = findViewById(R.id.buttonXemGiaTheoNam);
        recyclerViewDanhSachGia = findViewById(R.id.recyclerViewDanhSachGia);
        recyclerViewDanhSachGia.setLayoutManager(new LinearLayoutManager(this));
        buttonThemBangGiaMoi = findViewById(R.id.buttonThemBangGiaMoi);
        buttonSuaBangGia = findViewById(R.id.buttonSuaBangGia);

        listBangGia = new ArrayList<>();
        adapter = new BangGiaApDungAdapter(this, listBangGia, this);
        recyclerViewDanhSachGia.setAdapter(adapter);
    }

    private void loadDanhSachBangGia() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getAllBangGiaApDung().enqueue(new Callback<List<BangGiaApDung>>() {
            @Override
            public void onResponse(Call<List<BangGiaApDung>> call, Response<List<BangGiaApDung>> response) {
                if (response.isSuccessful()) {
                    listBangGia.clear();
                    listBangGia.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(QLTrangThaiActivity.this, "Lỗi tải danh sách bảng giá", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BangGiaApDung>> call, Throwable t) {
                Toast.makeText(QLTrangThaiActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinnerNam() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (int i = currentYear; i >= 2020; i--) {
            years.add(i);
        }
        ArrayAdapter<Integer> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinnerChonNamXem.setAdapter(adapterSpinner);
    }

    private void addEvents() {
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadDanhSachBangGia();
                    }
                });

        buttonXemGiaTheoNam.setOnClickListener(v -> {
            int selectedYear = (int) spinnerChonNamXem.getSelectedItem();
            Toast.makeText(this, "Xem giá năm " + selectedYear, Toast.LENGTH_SHORT).show();
            // TODO: Gọi API để lọc danh sách bảng giá theo năm
            getBangGiaForYear(selectedYear);
        });

        buttonThemBangGiaMoi.setOnClickListener(v -> {
            Intent intent = new Intent(QLTrangThaiActivity.this, FormBangGiaActivity.class);
            launcher.launch(intent);
        });

        buttonSuaBangGia.setOnClickListener(v -> {
            int selectedId = adapter.getSelectedItemId();
            if (selectedId != -1) {
                Intent intent = new Intent(QLTrangThaiActivity.this, FormBangGiaActivity.class);
                intent.putExtra("id_banggia_edit", selectedId);
                launcher.launch(intent);
            } else {
                Toast.makeText(this, "Vui lòng chọn một bảng giá để sửa (nhấn vào item)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(BangGiaApDung bangGia) {
        adapter.setSelectedPosition(listBangGia.indexOf(bangGia));
    }

    private void getBangGiaForYear(int year) {
        APIService api = RetrofitClient.getInstance().create(APIService.class);
        api.getBangGiaByYear(year).enqueue(new Callback<List<BangGiaApDung>>() {
            @Override
            public void onResponse(Call<List<BangGiaApDung>> call, Response<List<BangGiaApDung>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listBangGia = response.body();
                    adapter = new BangGiaApDungAdapter(QLTrangThaiActivity.this, listBangGia, QLTrangThaiActivity.this); // create new adapter
                    recyclerViewDanhSachGia.setAdapter(adapter);
                } else {
                    Toast.makeText(QLTrangThaiActivity.this, "Không tìm thấy bảng giá cho năm " + year, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BangGiaApDung>> call, Throwable t) {
                Toast.makeText(QLTrangThaiActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}