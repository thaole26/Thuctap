package com.example.myapplication.Activities;

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
import com.example.myapplication.R;
import com.example.myapplication.Requests.HoaDonRequest;
import com.example.myapplication.Requests.TinhTienRequest;
import com.example.myapplication.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinhTiendienActivity extends AppCompatActivity {
    private EditText edtInvoiceId, edtMeterId, edtEndIndex;
    private TextView tvStartIndex, tvPeriod, tvConsumption, tvTotalAmount;
    private Button btnCalculate, btnSaveTotal, btnCancel;
    private String madk;
    private int dntt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tinhtiendien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        edtInvoiceId = findViewById(R.id.edtInvoiceId);
        edtMeterId = findViewById(R.id.edtMeterId);
        edtEndIndex = findViewById(R.id.edtEndIndex);

        tvStartIndex = findViewById(R.id.tvStartIndex);
        tvPeriod = findViewById(R.id.tvPeriod);
        tvConsumption = findViewById(R.id.tvConsumption);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnSaveTotal = findViewById(R.id.btnSaveTotal);
        btnCancel = findViewById(R.id.btnCancel);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based
        int year = calendar.get(Calendar.YEAR);

        String ky = String.format("%02d/%d", month, year);
        tvPeriod.setText(ky+"");
    }

    private void addEvents() {
        edtMeterId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                madk = edtMeterId.getText().toString().trim();
                if (!madk.isEmpty()) {
                    getChiSoDauFromAPI(madk);
                }
            }
        });

        edtEndIndex.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus && madk != null && !edtEndIndex.getText().toString().isEmpty()) {
                int start = Integer.parseInt(tvStartIndex.getText().toString());
                int end = Integer.parseInt(edtEndIndex.getText().toString());
                if (end <= start) {
                    Toast.makeText(TinhTiendienActivity.this, "Chỉ số cuối phải lớn hơn chỉ số đầu", Toast.LENGTH_SHORT).show();
                } else {
                    dntt = end - start;
                    tvConsumption.setText(dntt + "kWh");
                }
            }
        });

        btnCalculate.setOnClickListener(view -> {
            TinhTienRequest request = new TinhTienRequest(dntt);
            APIService api = RetrofitClient.getInstance().create(APIService.class);

            api.tinhtiendien(request).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (response.isSuccessful()) {
                        double tongTien = response.body();
                        tvTotalAmount.setText(tongTien + " VNĐ");
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    Toast.makeText(TinhTiendienActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });

        });

        btnSaveTotal.setOnClickListener(view -> {
            String mahd = edtInvoiceId.getText().toString();
            String ky = tvPeriod.getText().toString();
            String tungay = getFirstDayOfMonth(ky);
            String denngay = getLastDayOfMonth(ky);
            int chisodau = Integer.parseInt(tvStartIndex.getText().toString());
            int chisocuoi = Integer.parseInt(edtEndIndex.getText().toString());
            String ngaylaphd = getCurrentDateTime();
            HoaDonRequest request = new HoaDonRequest(mahd, madk, ky, tungay, denngay, chisodau, chisocuoi, ngaylaphd, 0);

            APIService api = RetrofitClient.getInstance().create(APIService.class);
            api.taoHoadon(request).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(TinhTiendienActivity.this, "Lưu hoá đơn thành công!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(TinhTiendienActivity.this, "Lỗi khi lưu hoá đơn", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TinhTiendienActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCancel.setOnClickListener(view -> finish());
    }

    private void getChiSoDauFromAPI(String madk) {
        APIService api = RetrofitClient.getInstance().create(APIService.class);
        api.getChiSoDau(madk).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int chiSoDau = response.body(); // save to use later
                    tvStartIndex.setText(chiSoDau+"");
                } else {
                    Toast.makeText(TinhTiendienActivity.this, "Không tìm thấy chỉ số đầu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(TinhTiendienActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFirstDayOfMonth(String ky) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(ky);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return outputFormat.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private String getLastDayOfMonth(String ky) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(ky);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return outputFormat.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

}