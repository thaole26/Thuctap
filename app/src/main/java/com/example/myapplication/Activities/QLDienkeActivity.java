package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.myapplication.Adapters.DienKeAdapter;
import com.example.myapplication.Models.DienKe;
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
    RecyclerView rvMeters;
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
        getAllDienke();
        addEvents();
    }

    private void getAllDienke() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);

        apiService.getAllDienKe().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<DienKe>> call, Response<List<DienKe>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter.updateList(response.body());
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

    private void addEvents() {
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        getAllDienke();
                    }
                });

        adapter = new DienKeAdapter(list);
        adapter.setOnItemClickListener(dk -> {
            Intent intent = new Intent(this, CTDienkeActivity.class);
            intent.putExtra("madk", dk.getMadk());
            intent.putExtra("makh", dk.getMakh());
            intent.putExtra("ngaysx", dk.getNgaysx());
            intent.putExtra("ngaylap", dk.getNgaylap());
            intent.putExtra("mota", dk.getMota());
            intent.putExtra("trangthai", dk.getTrangthai());
            launcher.launch(intent);
        });

        rvMeters.setAdapter(adapter);
        rvMeters.setLayoutManager(new LinearLayoutManager(this));

        btnAddMeter.setOnClickListener(view -> {
            Intent intent = new Intent(QLDienkeActivity.this, ThemDienkeActivity.class);
            launcher.launch(intent);
        });
    }

    private void addControls() {
        edtSearchMeter = findViewById(R.id.edtSearchMeter);
        btnAddMeter = findViewById(R.id.btnAddMeter);
        rvMeters = findViewById(R.id.rvMeterList);
        list = new ArrayList<>();

    }
}
