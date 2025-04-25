package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.APIService;
import com.example.myapplication.R;
import com.example.myapplication.Requests.MucGiaChiTietRequest;
import com.example.myapplication.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaGiaDienActivity extends AppCompatActivity {
    private EditText edtTierName, edtStartKw, edtEndKw, edtUnitPrice;
    private Button btnSavePriceTier, btnBack;
    private TextView tvAddErrorMessage;
    private int id_mucgia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.suagiadien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_price_tier_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        Intent intent = getIntent();
        id_mucgia = intent.getIntExtra("id_mucgia", -1);
        edtTierName.setText(String.valueOf(intent.getStringExtra("tenbac")));
        edtStartKw.setText(String.valueOf(intent.getIntExtra("tu_kwh", 0)));
        edtEndKw.setText(String.valueOf(intent.getIntExtra("den_kwh", 0)));
        edtUnitPrice.setText(String.valueOf(intent.getDoubleExtra("dongia", 0)));

        btnSavePriceTier.setOnClickListener(v -> updateGiaDien());

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateGiaDien() {
        String tenbacStr = edtTierName.getText().toString().trim();
        String startStr = edtStartKw.getText().toString().trim();
        String endStr = edtEndKw.getText().toString().trim();
        String priceStr = edtUnitPrice.getText().toString().trim();

        if (tenbacStr.isEmpty() || startStr.isEmpty() || endStr.isEmpty() || priceStr.isEmpty()) {
            showError("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        int tu_kwh = Integer.parseInt(startStr);
        int den_kwh = Integer.parseInt(endStr);
        double don_gia = Double.parseDouble(priceStr);

        if (tu_kwh >= den_kwh) {
            showError("Số kWh bắt đầu phải nhỏ hơn số kWh kết thúc");
            return;
        }

        MucGiaChiTietRequest request = new MucGiaChiTietRequest(tenbacStr, tu_kwh, den_kwh, don_gia);

        APIService api = RetrofitClient.getInstance().create(APIService.class);
        api.updateTier(id_mucgia, request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SuaGiaDienActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showError("Lỗi khi cập nhật bậc. Có thể bậc bị trùng hoặc sai dữ liệu.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        tvAddErrorMessage.setText(message);
        tvAddErrorMessage.setVisibility(View.VISIBLE);
    }

    private void addControls() {
        edtTierName = findViewById(R.id.edtTierName);
        edtStartKw = findViewById(R.id.edtStartKw);
        edtEndKw = findViewById(R.id.edtEndKw);
        edtUnitPrice = findViewById(R.id.edtUnitPrice);
        tvAddErrorMessage = findViewById(R.id.tvAddErrorMessage);
        btnSavePriceTier = findViewById(R.id.btnSavePriceTier);
        btnBack = findViewById(R.id.btnBack);
    }
}