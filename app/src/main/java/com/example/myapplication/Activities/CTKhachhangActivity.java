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
import com.example.myapplication.Requests.KhachHangRequest;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTKhachhangActivity extends AppCompatActivity {
    private EditText edtCustomerId, edtCustomerName, edtCustomerPhone, edtCustomerAddress, edtCustomerIDNumber;
    private Button btnSaveUpdatedCustomer, btnBack, btnDeleteCustomer;
    private TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chitietkhachhang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.update_customer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        edtCustomerId = findViewById(R.id.edtUpdateCustomerId);
        edtCustomerName = findViewById(R.id.edtUpdateCustomerName);
        edtCustomerPhone = findViewById(R.id.edtUpdateCustomerPhone);
        edtCustomerAddress = findViewById(R.id.edtUpdateCustomerAddress);
        edtCustomerIDNumber = findViewById(R.id.edtUpdateCustomerIDNumber);
        btnSaveUpdatedCustomer = findViewById(R.id.btnSaveUpdatedCustomer);
        btnBack = findViewById(R.id.btnBack);
        btnDeleteCustomer = findViewById(R.id.btnDeleteCustomer);
        tvErrorMessage = findViewById(R.id.tvUpdateErrorMessage);

        Intent intent = getIntent();
        edtCustomerId.setText(intent.getStringExtra("makh"));
        edtCustomerName.setText(intent.getStringExtra("tenkh"));
        edtCustomerPhone.setText(intent.getStringExtra("dt"));
        edtCustomerAddress.setText(intent.getStringExtra("diachi"));
        edtCustomerIDNumber.setText(intent.getStringExtra("cmnd"));
    }

    private void addEvents() {
        btnSaveUpdatedCustomer.setOnClickListener(v -> {
            String makh = edtCustomerId.getText().toString().trim();
            String tenkh = edtCustomerName.getText().toString().trim();
            String sdt = edtCustomerPhone.getText().toString().trim();
            String diachi = edtCustomerAddress.getText().toString().trim();
            String cmnd = edtCustomerIDNumber.getText().toString().trim();

            if (tenkh.isEmpty()) {
                tvErrorMessage.setText("Tên khách hàng không được để trống");
                tvErrorMessage.setVisibility(View.VISIBLE);
                return;
            }

            // Hide error
            tvErrorMessage.setVisibility(View.GONE);

            // Send PUT request to API
            APIService api = RetrofitClient.getInstance().create(APIService.class);
            KhachHangRequest request = new KhachHangRequest(makh, tenkh, sdt, diachi, cmnd);

            api.suaKhachHang(makh, request).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CTKhachhangActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        try {
                            String msg = new JSONObject(response.errorBody().string()).getString("message");
                            tvErrorMessage.setText(msg);
                        } catch (Exception e) {
                            tvErrorMessage.setText("Cập nhật thất bại");
                        }
                        tvErrorMessage.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    tvErrorMessage.setText("Lỗi kết nối: " + t.getMessage());
                    tvErrorMessage.setVisibility(View.VISIBLE);
                }
            });
        });

        // Back button
        btnBack.setOnClickListener(v -> finish());

        btnDeleteCustomer.setOnClickListener(v -> {
            String makh = getIntent().getStringExtra("makh");

            APIService api = RetrofitClient.getInstance().create(APIService.class);
            api.xoaKhachHang(makh).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CTKhachhangActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(CTKhachhangActivity.this, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(CTKhachhangActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}