package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.MucGiaChiTiet;
import com.example.myapplication.R;
import java.util.List;

public class GiaDienAdapter extends RecyclerView.Adapter<GiaDienAdapter.GiaDienViewHolder> {

    private List<MucGiaChiTiet> mucGiaChiTietList;

    public GiaDienAdapter(List<MucGiaChiTiet> mucGiaChiTietList) {
        this.mucGiaChiTietList = mucGiaChiTietList;
    }

    @NonNull
    @Override
    public GiaDienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giadien, parent, false);
        return new GiaDienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaDienViewHolder holder, int position) {
        MucGiaChiTiet mucGiaChiTiet = mucGiaChiTietList.get(position);
        holder.tvMabac.setText("Mã bậc: " + mucGiaChiTiet.getId_mucgia());
        holder.tvTenbac.setText("Tên bậc: " + mucGiaChiTiet.getTen_bac());
        holder.tvTusokw.setText("Từ số kWh: " + mucGiaChiTiet.getTu_kwh());
        holder.tvDensokw.setText("Đến số kWh: " + mucGiaChiTiet.getDen_kwh());
        holder.tvDongia.setText("Đơn giá: " + mucGiaChiTiet.getDon_gia());
    }

    @Override
    public int getItemCount() {
        return mucGiaChiTietList.size();
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
        }
    }

    public void updateList(List<MucGiaChiTiet> newList) {
        this.mucGiaChiTietList = newList;
        notifyDataSetChanged();
    }
}
