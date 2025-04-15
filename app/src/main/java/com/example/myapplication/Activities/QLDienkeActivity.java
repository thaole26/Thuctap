package com.example.myapplication.Activities;

import android.os.Bundle;
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

import com.example.myapplication.APIService;
import com.example.myapplication.Adapters.DienKeAdapter;
import com.example.myapplication.Models.DienKe;
import com.example.myapplication.Models.KhachHang;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLDienkeActivity extends AppCompatActivity {
    EditText edtSearchMeter;
    Button btnAddMeter, btnEditMeter, btnDeleteMeter;
    ListView lvMeters;
    List<DienKe> list;
    DienKeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quanlydienke);
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

        apiService.getAllDienKe().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<DienKe>> call, Response<List<DienKe>> response) {
                if (response.isSuccessful()) {
                    List<DienKe> list = response.body();
                    adapter = new DienKeAdapter(QLDienkeActivity.this, list);
                    lvMeters.setAdapter(adapter);
                } else {
                    Toast.makeText(QLDienkeActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DienKe>> call, Throwable t) {
                Toast.makeText(QLDienkeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        edtSearchMeter = findViewById(R.id.edtSearchMeter);
        btnAddMeter = findViewById(R.id.btnAddMeter);
        btnEditMeter = findViewById(R.id.btnEditMeter);
        btnDeleteMeter = findViewById(R.id.btnDeleteMeter);
        lvMeters = findViewById(R.id.lvMeters);
        list = new ArrayList<>();
        adapter = new DienKeAdapter(this,list);
        lvMeters.setAdapter(adapter);
    }
}
