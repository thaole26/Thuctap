package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIService;
import com.example.myapplication.Adapters.KhachHangAdapter;
import com.example.myapplication.Models.KhachHang;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLKhachhangActivity extends AppCompatActivity {
    EditText edtSearchCustomer;
    Button btnAddCustomer, btnUpdateCustomer, btnDeleteCustomer;
    RecyclerView rvCustomerList;
    List<KhachHang> list;
    KhachHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quanlykhachhang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        getAllKhachHang();
        addEvents();
    }

    private void addEvents() {
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    getAllKhachHang();
                }
        });

        adapter.setOnItemClickListener(kh -> {
            Intent intent = new Intent(QLKhachhangActivity.this, CTKhachhangActivity.class);
            intent.putExtra("makh", kh.getMakh());
            intent.putExtra("tenkh", kh.getTenkh());
            intent.putExtra("diachi", kh.getDiachi());
            intent.putExtra("dt", kh.getDt());
            intent.putExtra("cmnd", kh.getCmnd());
            launcher.launch (intent);
        });
        rvCustomerList.setAdapter(adapter);
        rvCustomerList.setLayoutManager(new LinearLayoutManager(this));

        edtSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAddCustomer.setOnClickListener(view -> {
            Intent intent = new Intent(QLKhachhangActivity.this, ThemKhachhangActivity.class);
            launcher.launch(intent);
        });

    }

    private void getAllKhachHang() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);

        apiService.getAllKhachHang().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter.updateList(response.body());
                } else {
                    Toast.makeText(QLKhachhangActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable t) {
                Toast.makeText(QLKhachhangActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        edtSearchCustomer = findViewById(R.id.edtSearchCustomer);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        rvCustomerList = findViewById(R.id.rvCustomerList);
        list = new ArrayList<>();
        adapter = new KhachHangAdapter(list);

    }
}