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
import com.example.myapplication.RetrofitClient;
import com.example.myapplication.Requests.KhachHangRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemKhachhangActivity extends AppCompatActivity {
    EditText edtCustomerId, edtCustomerName, edtCustomerPhone, edtCustomerAddress, edtCustomerIDNumber;
    Button btnSaveCustomer, btnBack;
    TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.themkhachhang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_customer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        edtCustomerId = findViewById(R.id.edtCustomerId);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtCustomerPhone = findViewById(R.id.edtCustomerPhone);
        edtCustomerAddress = findViewById(R.id.edtCustomerAddress);
        edtCustomerIDNumber = findViewById(R.id.edtCustomerIDNumber);
        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);
        btnBack = findViewById(R.id.btnBack);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
    }

    private void addEvents() {
        btnSaveCustomer.setOnClickListener(v -> {
            String id = edtCustomerId.getText().toString().trim();
            String name = edtCustomerName.getText().toString().trim();
            String phone = edtCustomerPhone.getText().toString().trim();
            String address = edtCustomerAddress.getText().toString().trim();
            String idnumber = edtCustomerIDNumber.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || idnumber.isEmpty()) {
                tvErrorMessage.setText("Vui lòng nhập đầy đủ thông tin!");
                tvErrorMessage.setVisibility(View.VISIBLE);
            } else {
                tvErrorMessage.setVisibility(View.GONE);
                SendRequest(id, name, phone, address, idnumber);
            }

        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void SendRequest(String id, String name, String phone, String address, String idnumber) {
        KhachHangRequest request = new KhachHangRequest(id, name, phone, address, idnumber);
        APIService api = RetrofitClient.getInstance().create(APIService.class);

        api.themKhachHang(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ThemKhachhangActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    if (response.code() == 500) {
                        tvErrorMessage.setText("Thêm thất bại: " + response.code());
                    } else {
                        tvErrorMessage.setText("Mã khách hàng và cmnd không được trùng");
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

    }
}