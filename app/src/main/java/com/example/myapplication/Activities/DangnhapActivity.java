package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.Requests.LoginRequest;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangnhapActivity extends AppCompatActivity {
    EditText txtUsername,txtPass;
    Button btnLogin;
    TextView tvDangki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dangnhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addEvents() {
        tvDangki.setOnClickListener(view -> {
            Intent intent = new Intent(DangnhapActivity.this, DangkiActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            if (txtUsername.getText().toString().trim().isEmpty() || txtPass.getText().toString().trim().isEmpty()) {
                Toast.makeText(this,"Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                String username = txtUsername.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();

                APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                LoginRequest loginRequest = new LoginRequest(username,pass);
                apiService.dangnhap(loginRequest).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Intent intent = new Intent(DangnhapActivity.this, MenuActivity.class);
                            intent.putExtra("manv",response.body());
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("login",response.code()+"");
                            Toast.makeText(DangnhapActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("login",t.getMessage() + " ");
                        Toast.makeText(DangnhapActivity.this, "Lỗi đăng nhập tài khoản. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void addControls() {
        txtUsername = findViewById(R.id.edtUsername);
        txtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvDangki = findViewById(R.id.tvRegister);
    }
}
