package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.Models.DienKe;
import com.example.myapplication.R;

import java.util.List;

public class DienKeAdapter extends ArrayAdapter<DienKe> {
    private Context context;
    private List<DienKe> dienKeList;

    public DienKeAdapter(Context context, List<DienKe> dienKeList) {
        super(context, 0, dienKeList);
        this.context = context;
        this.dienKeList = dienKeList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DienKe dienKe = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dienke, parent, false);
        }

        TextView tvMaDienKe = convertView.findViewById(R.id.tvMaDienKe);
        TextView tvMaKH = convertView.findViewById(R.id.tvMaKH);
        TextView tvNgaySX = convertView.findViewById(R.id.tvNgaySX);
        TextView tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
        TextView tvMota = convertView.findViewById(R.id.tvMota);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);

        tvMaDienKe.setText("Mã ĐK: " + dienKe.getMadk());
        tvMaKH.setText("Mã KH: " + dienKe.getMakh());
        tvNgaySX.setText("Ngày SX: " + dienKe.getNgaysx());
        tvNgayLap.setText("Ngày Lắp: " + dienKe.getNgaylap());
        tvMota.setText("Mô tả: " + dienKe.getMota());
        tvTrangThai.setText("Trạng thái: " + (dienKe.getTrangthai() == 1 ? "Đang hoạt động" : "Ngưng hoạt động"));

        return convertView;
    }
}
