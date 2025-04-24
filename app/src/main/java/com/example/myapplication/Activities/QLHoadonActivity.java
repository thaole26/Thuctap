package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIService;
import com.example.myapplication.Adapters.HoaDonAdapter;
import com.example.myapplication.Models.HoaDon;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLHoadonActivity extends AppCompatActivity {

    private EditText edtSearchInvoice;
    private RecyclerView rvInvoiceList;
    private Button btnCreate, btnCalculate, btnViewDetail, btnDelete;
    private HoaDonAdapter adapter;
    private List<HoaDon> hoaDonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quanlyhoadon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        fetchHoaDonList();
    }

    private void addEvents() {
        edtSearchInvoice.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

//        btnCreate.setOnClickListener(v -> {
//            startActivity(new Intent(QLHoadonActivity.this, TaoHoadonActivity.class));
//        });

        btnCalculate.setOnClickListener(v -> {
            startActivity(new Intent(QLHoadonActivity.this, TinhTiendienActivity.class));
        });
    }

    private void fetchHoaDonList() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        Call<List<HoaDon>> call = apiService.getAllHoaDon();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hoaDonList.clear();
                    hoaDonList.addAll(response.body());
                    adapter.updateList(hoaDonList);
                } else {
                    Toast.makeText(QLHoadonActivity.this, "Không thể tải hóa đơn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                Toast.makeText(QLHoadonActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addControls() {
        edtSearchInvoice = findViewById(R.id.edtSearchInvoice);
        rvInvoiceList = findViewById(R.id.rvInvoiceList);
        //btnCreate = findViewById(R.id.btnCreateInvoice);
        btnCalculate = findViewById(R.id.btnCalculateBill);
        btnViewDetail = findViewById(R.id.btnViewInvoiceDetails);
        btnDelete = findViewById(R.id.btnDeleteInvoice);
        adapter = new HoaDonAdapter(hoaDonList);
        rvInvoiceList.setLayoutManager(new LinearLayoutManager(this));
        rvInvoiceList.setAdapter(adapter);
    }

    private void filter(String query) {
        List<HoaDon> filtered = new ArrayList<>();
        for (HoaDon hd : hoaDonList) {
            if (hd.getMahd().toLowerCase().contains(query.toLowerCase()) ||
                    hd.getMadk().toLowerCase().contains(query.toLowerCase()) ||
                    hd.getKy().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(hd);
            }
        }
        adapter.updateList(filtered);
    }
}