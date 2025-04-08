package com.example.myapplication.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIService;
import com.example.myapplication.KhachHangAdapter;
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
        addEvents();
    }

    private void addEvents() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);

        apiService.getAllKhachHang().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                if (response.isSuccessful()) {
                    List<KhachHang> list = response.body();
                    adapter = new KhachHangAdapter(list);
                    rvCustomerList.setAdapter(adapter);
                } else {
                    Toast.makeText(QLKhachhangActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable t) {
                Toast.makeText(QLKhachhangActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
    }

    private void addControls() {
        edtSearchCustomer = findViewById(R.id.edtSearchCustomer);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        btnUpdateCustomer = findViewById(R.id.btnUpdateCustomer);
        btnDeleteCustomer = findViewById(R.id.btnDeleteCustomer);
        rvCustomerList = findViewById(R.id.rvCustomerList);
        list = new ArrayList<>();
        adapter = new KhachHangAdapter(list);
        rvCustomerList.setAdapter(adapter);
        rvCustomerList.setLayoutManager(new LinearLayoutManager(this));
    }
}