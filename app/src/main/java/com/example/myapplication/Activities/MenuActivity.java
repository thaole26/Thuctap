package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class MenuActivity extends AppCompatActivity {
    Button btnCustomer, btnMeter, btnPrice, btnInvoice, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manhinhchinh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addEvents() {
        Log.d("id", getIntent().getIntExtra("manv",0)+"");
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, DangnhapActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addControls() {
        btnCustomer = findViewById(R.id.btnCustomer);
        btnMeter = findViewById(R.id.btnMeter);
        btnPrice = findViewById(R.id.btnPrice);
        btnInvoice = findViewById(R.id.btnInvoice);
        btnLogout = findViewById(R.id.btnLogout);
    }
}
