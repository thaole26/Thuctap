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
import com.example.myapplication.Models.DienKe;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTDienkeActivity extends AppCompatActivity {
    private EditText edtDienKeId, edtDienKeCustomerId, edtNgaysx, edtNgaylap, edtMota, edtTrangthai;
    private Button btnSaveDienKe, btnBackDienKe, btnDeleteDienKe;
    private TextView tvError;
    private String madk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chitietdienke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dienke_detail_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        Intent intent = getIntent();
        madk = intent.getStringExtra("madk");

        edtDienKeId.setText(madk);
        edtDienKeCustomerId.setText(intent.getStringExtra("makh"));
        edtNgaysx.setText(intent.getStringExtra("ngaysx"));
        edtNgaylap.setText(intent.getStringExtra("ngaylap"));
        edtMota.setText(intent.getStringExtra("mota"));
        edtTrangthai.setText(String.valueOf(intent.getIntExtra("trangthai", 0)));

        btnSaveDienKe.setOnClickListener(v -> updateDienKe());
        btnDeleteDienKe.setOnClickListener(v -> deleteDienKe());
        btnBackDienKe.setOnClickListener(v -> finish());
    }

    private void updateDienKe() {
        DienKe dk = new DienKe();
        dk.setMadk(madk);
        dk.setMakh(edtDienKeCustomerId.getText().toString().trim());
        dk.setNgaysx(edtNgaysx.getText().toString().trim());
        dk.setNgaylap(edtNgaylap.getText().toString().trim());
        dk.setMota(edtMota.getText().toString().trim());

        try {
            dk.setTrangthai(Integer.parseInt(edtTrangthai.getText().toString().trim()));
        } catch (NumberFormatException e) {
            tvError.setText("Trạng thái phải là số");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        APIService api = RetrofitClient.getInstance().create(APIService.class);
        api.suaDienKe(madk, dk).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CTDienkeActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    tvError.setText("Lỗi khi cập nhật");
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvError.setText("Lỗi kết nối");
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void deleteDienKe() {
        APIService api = RetrofitClient.getInstance().create(APIService.class);
        api.xoaDienKe(madk).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CTDienkeActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    tvError.setText("Không thể xóa điện kế");
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvError.setText("Lỗi kết nối khi xóa");
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addControls() {
        edtDienKeId = findViewById(R.id.edtDienKeId);
        edtDienKeCustomerId = findViewById(R.id.edtDienKeCustomerId);
        edtNgaysx = findViewById(R.id.edtNgaysx);
        edtNgaylap = findViewById(R.id.edtNgaylap);
        edtMota = findViewById(R.id.edtMota);
        edtTrangthai = findViewById(R.id.edtTrangthai);

        btnSaveDienKe = findViewById(R.id.btnSaveDienKe);
        btnBackDienKe = findViewById(R.id.btnBackDienKe);
        btnDeleteDienKe = findViewById(R.id.btnDeleteDienKe);

        tvError = findViewById(R.id.tvUpdateDienkeError);
    }
}
