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
import com.example.myapplication.Adapters.KhachHangAdapter;
import com.example.myapplication.Models.GiaDien;
import com.example.myapplication.Models.KhachHang;
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
    List<GiaDien> list;
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
            public void onResponse(Call<List<GiaDien>> call, Response<List<GiaDien>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new GiaDienAdapter(list);
                    rvView.setAdapter(adapter);
                } else {
                    Toast.makeText(QLGiadienActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GiaDien>> call, Throwable t) {
                Toast.makeText(QLGiadienActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                List<GiaDien> filteredList = new ArrayList<>();

                for (GiaDien item : list) {
                    if (String.valueOf(item.getMabac()).contains(query)) {
                        filteredList.add(item);
                    } else {
                        try {
                            int input = Integer.parseInt(query);
                            if (item.getTusokw()>item.getDensokw() && input >= item.getTusokw()) {
                                filteredList.add(item);
                            } else
                            if (input >= item.getTusokw() && input <= item.getDensokw()) {
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
        edtSearch = findViewById(R.id.edtSearch);
        rvView = findViewById(R.id.rvView);
        btnGia = findViewById(R.id.btnGia);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        list = new ArrayList<>();
        adapter = new GiaDienAdapter(list);
        rvView.setAdapter(adapter);
        rvView.setLayoutManager(new LinearLayoutManager(this));
    }
}