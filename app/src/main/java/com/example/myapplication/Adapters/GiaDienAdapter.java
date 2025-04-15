package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.GiaDien;
import com.example.myapplication.R;
import java.util.List;

public class GiaDienAdapter extends RecyclerView.Adapter<GiaDienAdapter.GiaDienViewHolder> {

    private List<GiaDien> giaDienList;

    public GiaDienAdapter(List<GiaDien> giaDienList) {
        this.giaDienList = giaDienList;
    }

    @NonNull
    @Override
    public GiaDienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giadien, parent, false);
        return new GiaDienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaDienViewHolder holder, int position) {
        GiaDien giaDien = giaDienList.get(position);
        holder.tvMabac.setText("Mã bậc: " + giaDien.getMabac());
        holder.tvTenbac.setText("Tên bậc: " + giaDien.getTenbac());
        holder.tvTusokw.setText("Từ số kWh: " + giaDien.getTusokw());
        holder.tvDensokw.setText("Đến số kWh: " + giaDien.getDensokw());
        holder.tvDongia.setText("Đơn giá: " + giaDien.getDongia());
        holder.tvNgayapdung.setText("Ngày áp dụng: " + giaDien.getNgayapdung());
    }

    @Override
    public int getItemCount() {
        return giaDienList.size();
    }

    public static class GiaDienViewHolder extends RecyclerView.ViewHolder {
        TextView tvMabac, tvTenbac, tvTusokw, tvDensokw, tvDongia, tvNgayapdung;

        public GiaDienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMabac = itemView.findViewById(R.id.tv_mabac);
            tvTenbac = itemView.findViewById(R.id.tv_tenbac);
            tvTusokw = itemView.findViewById(R.id.tv_tusokw);
            tvDensokw = itemView.findViewById(R.id.tv_densokw);
            tvDongia = itemView.findViewById(R.id.tv_dongia);
            tvNgayapdung = itemView.findViewById(R.id.tv_ngayapdung);
        }
    }

    public void updateList(List<GiaDien> newList) {
        this.giaDienList = newList;
        notifyDataSetChanged();
    }
}
