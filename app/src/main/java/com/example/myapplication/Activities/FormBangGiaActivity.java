package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.APIService;
import com.example.myapplication.Models.BangGiaApDung;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormBangGiaActivity extends AppCompatActivity {

    TextView textViewFormBangGiaTitle;
    EditText editTextTenBangGia;
    EditText editTextNgayApDung;
    EditText editTextNgayKetThuc;
    Switch switchTrangThai;
    EditText editTextMoTaBangGia;
    Button buttonLuuBangGia;
    Button buttonHuyBangGia;

    private int idBangGiaToEdit = -1; // Để theo dõi nếu đang sửa
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_sua_bang_gia);

        addControls();
        loadDataForEdit();
        addEvents();
    }

    private void addControls() {
        textViewFormBangGiaTitle = findViewById(R.id.textViewFormBangGiaTitle);
        editTextTenBangGia = findViewById(R.id.editTextTenBangGia);
        editTextNgayApDung = findViewById(R.id.editTextNgayApDung);
        editTextNgayKetThuc = findViewById(R.id.editTextNgayKetThuc);
        switchTrangThai = findViewById(R.id.switchTrangThai);
        editTextMoTaBangGia = findViewById(R.id.editTextMoTaBangGia);
        buttonLuuBangGia = findViewById(R.id.buttonLuuBangGia);
        buttonHuyBangGia = findViewById(R.id.buttonHuyBangGia);
    }

    private void loadDataForEdit() {
        idBangGiaToEdit = getIntent().getIntExtra("id_banggia_edit", -1);
        if (idBangGiaToEdit != -1) {
            textViewFormBangGiaTitle.setText("Sửa Thông tin Bảng Giá");
            // Gọi API để lấy thông tin bảng giá theo ID và điền vào các trường
            APIService apiService = RetrofitClient.getInstance().create(APIService.class);
            apiService.getBangGiaApDungById(idBangGiaToEdit).enqueue(new Callback<BangGiaApDung>() {
                @Override
                public void onResponse(Call<BangGiaApDung> call, Response<BangGiaApDung> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        BangGiaApDung bangGia = response.body();
                        editTextTenBangGia.setText(bangGia.getTen_banggia());
                        editTextNgayApDung.setText(dateFormat.format(bangGia.getNgay_apdung()));
                        if (bangGia.getNgay_ketthuc() != null) {
                            editTextNgayKetThuc.setText(dateFormat.format(bangGia.getNgay_ketthuc()));
                        }
                        switchTrangThai.setChecked(bangGia.getTrangthai() == 1);
                        editTextMoTaBangGia.setText(bangGia.getMota());
                    } else {
                        Toast.makeText(FormBangGiaActivity.this, "Lỗi tải thông tin bảng giá để sửa", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<BangGiaApDung> call, Throwable t) {
                    Toast.makeText(FormBangGiaActivity.this, "Lỗi kết nối API khi tải thông tin", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            textViewFormBangGiaTitle.setText("Thêm Thông tin Bảng Giá");
        }
    }

    private void addEvents() {
        editTextNgayApDung.setOnClickListener(v -> showDatePickerDialog(editTextNgayApDung));
        editTextNgayKetThuc.setOnClickListener(v -> showDatePickerDialog(editTextNgayKetThuc));

        buttonLuuBangGia.setOnClickListener(v -> saveBangGia());
        buttonHuyBangGia.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearPicker, monthPicker, dayPicker) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(yearPicker, monthPicker, dayPicker);
                    editText.setText(dateFormat.format(selectedCalendar.getTime()));
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveBangGia() {
        String tenBangGia = editTextTenBangGia.getText().toString().trim();
        String ngayApDungStr = editTextNgayApDung.getText().toString().trim();
        String ngayKetThucStr = editTextNgayKetThuc.getText().toString().trim();
        boolean trangThai = switchTrangThai.isChecked();
        String moTa = editTextMoTaBangGia.getText().toString().trim();

        if (tenBangGia.isEmpty() || ngayApDungStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên bảng giá và ngày áp dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Date ngayApDung = dateFormat.parse(ngayApDungStr);
            Date ngayKetThuc = null;
            if (!ngayKetThucStr.isEmpty()) {
                ngayKetThuc = dateFormat.parse(ngayKetThucStr);
                if (ngayKetThuc.before(ngayApDung)) {
                    Toast.makeText(this, "Ngày kết thúc không được trước ngày áp dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            BangGiaApDung bangGia = new BangGiaApDung();
            bangGia.setTen_banggia(tenBangGia);
            bangGia.setNgay_apdung(ngayApDung);
            bangGia.setNgay_ketthuc(ngayKetThuc);
            bangGia.setTrangthai((byte) (trangThai ? 1 : 0));
            bangGia.setMota(moTa);

            APIService apiService = RetrofitClient.getInstance().create(APIService.class);
            Call<BangGiaApDung> call;

            if (idBangGiaToEdit != -1) {
                bangGia.setId_banggia(idBangGiaToEdit);
                call = apiService.updateBangGiaApDung(idBangGiaToEdit, bangGia);
            } else {
                call = apiService.createBangGiaApDung(bangGia);
            }

            call.enqueue(new Callback<BangGiaApDung>() {
                @Override
                public void onResponse(Call<BangGiaApDung> call, Response<BangGiaApDung> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(FormBangGiaActivity.this, (idBangGiaToEdit != -1 ? "Cập nhật" : "Thêm") + " bảng giá thành công", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    } else {
                        Toast.makeText(FormBangGiaActivity.this, "Lỗi khi " + (idBangGiaToEdit != -1 ? "cập nhật" : "thêm") + " bảng giá", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BangGiaApDung> call, Throwable t) {
                    Toast.makeText(FormBangGiaActivity.this, "Lỗi kết nối API khi " + (idBangGiaToEdit != -1 ? "cập nhật" : "thêm") + " bảng giá: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (ParseException e) {
            Toast.makeText(this, "Định dạng ngày không hợp lệ (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
        }
    }
}