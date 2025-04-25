package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.myapplication.Models.MucGiaChiTiet;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.MucGiaChiTietAdapter;
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
    List<MucGiaChiTiet> list;
    MucGiaChiTietAdapter adapter;

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
        getAllGiaDien();
        addEvents();
    }

    private void getAllGiaDien() {
        int id_banggia = getIntent().getIntExtra("id_banggia",0);
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);

        apiService.getAllGiaDienByBangGiaID(id_banggia).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<MucGiaChiTiet>> call, Response<List<MucGiaChiTiet>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter.updateList(response.body());
                } else {
                    Toast.makeText(QLGiadienActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MucGiaChiTiet>> call, Throwable t) {
                Toast.makeText(QLGiadienActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvents() {
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    getAllGiaDien();
                }
            });

        adapter.setOnItemClickListener(giaDien -> {
            Intent intent = new Intent(this, SuaGiaDienActivity.class);
            intent.putExtra("id_mucgia", giaDien.getId_mucgia());
            intent.putExtra("id_banggia", giaDien.getId_banggia());
            intent.putExtra("bac", giaDien.getBac());
            intent.putExtra("tenbac", giaDien.getTen_bac());
            intent.putExtra("tu_kwh", giaDien.getTu_kwh());
            intent.putExtra("den_kwh", giaDien.getDen_kwh());
            intent.putExtra("dongia", giaDien.getDon_gia());
            launcher.launch(intent);
        });
        rvView.setAdapter(adapter);
        rvView.setLayoutManager(new LinearLayoutManager(this));

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
        list = new ArrayList<>();
        adapter = new MucGiaChiTietAdapter(list);
    }
}