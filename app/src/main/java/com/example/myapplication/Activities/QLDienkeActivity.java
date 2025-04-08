package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class QLDienkeActivity extends AppCompatActivity {
    EditText edtSearchMeter;
    Button btnAddMeter, btnEditMeter, btnDeleteMeter;
    ListView lvMeters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quanlydienke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        edtSearchMeter = findViewById(R.id.edtSearchMeter);
        btnAddMeter = findViewById(R.id.btnAddMeter);
        btnEditMeter = findViewById(R.id.btnEditMeter);
        btnDeleteMeter = findViewById(R.id.btnDeleteMeter);
        lvMeters = findViewById(R.id.lvMeters);
    }
}
