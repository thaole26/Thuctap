package com.example.myapplication.Activities;

import android.os.Bundle;
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
import com.example.myapplication.Requests.RegisterRequest;
import com.example.myapplication.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangkiActivity extends AppCompatActivity {
    EditText txtUsername, txtName, txtSDT, txtEmail, txtPass, txtRePass;
    Button btnRegister;
    TextView tvDangnhap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dangki);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        tvDangnhap.setOnClickListener(view -> finish());

        btnRegister.setOnClickListener(view -> {
            if (txtUsername.getText().toString().trim().isEmpty() ||
                txtPass.getText().toString().trim().isEmpty() ||
                txtName.getText().toString().trim().isEmpty()) {
                Toast.makeText(this,"Vui lòng điền đầy đủ tên đăng nhập, họ tên và mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                if (!txtPass.getText().toString().equals(txtRePass.getText().toString()))
                    Toast.makeText(this,"Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                else {
                    String username = txtUsername.getText().toString().trim();
                    String name = txtName.getText().toString().trim();
                    String sdt = txtSDT.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    String matkhau = txtPass.getText().toString();

                    APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                    RegisterRequest registerRequest = new RegisterRequest(username, name, sdt, email, matkhau);
                    apiService.dangkiNhanVien(registerRequest).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(DangkiActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (response.code() == 409) {
                                Toast.makeText(DangkiActivity.this, "Tên đăng nhập hoặc email đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(DangkiActivity.this, "Lỗi đăng ký tài khoản. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void addControls() {
        txtUsername = findViewById(R.id.edtUsername);
        txtName = findViewById(R.id.edtName);
        txtSDT = findViewById(R.id.edtSDT);
        txtEmail = findViewById(R.id.edtEmail);
        txtPass = findViewById(R.id.edtPassword);
        txtRePass = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvDangnhap = findViewById(R.id.tvLogin);
    }
}
