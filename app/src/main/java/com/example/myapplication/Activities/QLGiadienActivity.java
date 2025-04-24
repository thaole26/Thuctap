package com.example.myapplication.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIService;
import com.example.myapplication.Adapters.GiaDienAdapter;
import com.example.myapplication.Models.MucGiaChiTiet;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLGiadienActivity extends AppCompatActivity {
    TextView tvTitle;
    EditText edtSearch;
    RecyclerView rvView;
    Button btnGia, btnUpdate, btnDelete;
    List<MucGiaChiTiet> list;
    GiaDienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quanlygiadien);
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

        apiService.getAllGiaDien().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<MucGiaChiTiet>> call, Response<List<MucGiaChiTiet>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new GiaDienAdapter(list);
                    rvView.setAdapter(adapter);
                } else {
                    Toast.makeText(QLGiadienActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MucGiaChiTiet>> call, Throwable t) {
                Toast.makeText(QLGiadienActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                List<MucGiaChiTiet> filteredList = new ArrayList<>();

                for (MucGiaChiTiet item : list) {
                    if (String.valueOf(item.getId_mucgia()).contains(query)) {
                        filteredList.add(item);
                    } else {
                        try {
                            int input = Integer.parseInt(query);
                            if (item.getTu_kwh()>item.getDen_kwh() && input >= item.getTu_kwh()) {
                                filteredList.add(item);
                            } else
                            if (input >= item.getTu_kwh() && input <= item.getDen_kwh()) {
                                filteredList.add(item);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
                adapter.updateList(filteredList); // method in adapter to refresh list
            }

            @Override public void afterTextChanged(Editable s) {}
        });

    }

    private void addControls() {
        tvTitle = findViewById(R.id.tvTitle);
        edtSearch = findViewById(R.id.edtSearchPrice);
        rvView = findViewById(R.id.rvPriceList);
        btnGia = findViewById(R.id.btnAddPrice);
        list = new ArrayList<>();
        adapter = new GiaDienAdapter(list);
        rvView.setAdapter(adapter);
        rvView.setLayoutManager(new LinearLayoutManager(this));
    }
}