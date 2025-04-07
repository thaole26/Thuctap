package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.APIService;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

public class DangnhapActivity extends AppCompatActivity {
    EditText txtName,txtPass;

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

        APIService apiService = RetrofitClient.getInstance().create(APIService.class);

        addControls();
    }

    private void addControls() {
        txtName = findViewById(R.id.edtUsername);
        txtPass = findViewById(R.id.edtPassword);
    }
}
