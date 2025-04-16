package com.example.myapplication.Activities;

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
import com.example.myapplication.Requests.DienKeRequest;
import com.example.myapplication.RetrofitClient;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemDienkeActivity extends AppCompatActivity {
    private EditText edtMeterId,edtMeterCustomer, edtMeterType, edtInstallationDate, edtInstallationLocation, edtMota, edtTrangthai;
    private Button btnSaveMeter, btnBack;
    private TextView tvAddErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.themdienke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_meter_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSaveMeter.setOnClickListener(v -> {
            String madk = edtMeterId.getText().toString().trim();
            String makh = edtMeterCustomer.getText().toString().trim();
            String ngaySX = edtMeterType.getText().toString().trim();
            String ngayLap = edtInstallationDate.getText().toString().trim();
            String diachi = edtInstallationLocation.getText().toString().trim();
            String mota = edtMota.getText().toString().trim();
            String trangthai = edtTrangthai.getText().toString().trim();

            if (madk.isEmpty() || makh.isEmpty() || ngaySX.isEmpty() || ngayLap.isEmpty() || trangthai.isEmpty()) {
                tvAddErrorMessage.setText("Vui lòng nhập đầy đủ thông tin!");
                tvAddErrorMessage.setVisibility(View.VISIBLE);
                return;
            }

            int trthai = 0;
            try {
                trthai = Integer.parseInt(trangthai);
            } catch (NumberFormatException e) {
                tvAddErrorMessage.setText("Trạng thái phải là số");
                tvAddErrorMessage.setVisibility(View.VISIBLE);
                return;
            }
            if (trthai != 0 && trthai != 1) {
                tvAddErrorMessage.setText("Trạng thái chỉ có thể là 0 hoặc 1");
                tvAddErrorMessage.setVisibility(View.VISIBLE);
                return;
            }

            tvAddErrorMessage.setVisibility(View.GONE);

            DienKeRequest request = new DienKeRequest(madk, makh, ngaySX, ngayLap, diachi, mota, trthai);
            APIService api = RetrofitClient.getInstance().create(APIService.class);
            api.themDienKe(request).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ThemDienkeActivity.this, "Thêm điện kế thành công", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        try {
                            String error = new JSONObject(response.errorBody().string()).getString("message");
                            tvAddErrorMessage.setText(error);
                        } catch (Exception e) {
                            tvAddErrorMessage.setText("Thêm điện kế thất bại");
                        }
                        tvAddErrorMessage.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    tvAddErrorMessage.setText("Lỗi kết nối: " + t.getMessage());
                    tvAddErrorMessage.setVisibility(View.VISIBLE);
                }
            });
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void addControls() {
        edtMeterId = findViewById(R.id.edtMeterId);
        edtMeterType = findViewById(R.id.edtMeterType);
        edtMeterCustomer = findViewById(R.id.edtMeterCustomer);
        edtInstallationDate = findViewById(R.id.edtInstallationDate);
        edtInstallationLocation = findViewById(R.id.edtInstallationLocation);
        edtMota = findViewById(R.id.edtMota);
        edtTrangthai = findViewById(R.id.edtTrangthai);
        btnSaveMeter = findViewById(R.id.btnSaveMeter);
        btnBack = findViewById(R.id.btnBack);
        tvAddErrorMessage = findViewById(R.id.tvAddErrorMessage);
    }
}