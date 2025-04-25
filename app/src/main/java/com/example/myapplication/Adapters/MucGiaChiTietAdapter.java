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

public class MucGiaChiTietAdapter extends RecyclerView.Adapter<MucGiaChiTietAdapter.ViewHolder> {

    private List<MucGiaChiTiet> MucGiaChiTietList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MucGiaChiTiet MucGiaChiTiet);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MucGiaChiTietAdapter(List<MucGiaChiTiet> MucGiaChiTietList) {
        this.MucGiaChiTietList = MucGiaChiTietList;
    }

    @NonNull
    @Override
    public MucGiaChiTietAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_giadien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MucGiaChiTietAdapter.ViewHolder holder, int position) {
        MucGiaChiTiet item = MucGiaChiTietList.get(position);
        holder.tvMabac.setText("Mã bậc: " + item.getId_mucgia());
        holder.tvTenbac.setText("Tên bậc: " + item.getTen_bac());
        holder.tvTuso.setText("Từ (kWh): " + item.getTu_kwh());
        holder.tvDenso.setText("Đến (kWh): " + item.getDen_kwh());
        holder.tvDongia.setText("Đơn giá: " + item.getDon_gia() + "đ");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return MucGiaChiTietList.size();
    }

    public void updateList(List<MucGiaChiTiet> newList) {
        MucGiaChiTietList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMabac, tvTenbac, tvTuso, tvDenso, tvDongia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMabac = itemView.findViewById(R.id.tv_mabac);
            tvTenbac = itemView.findViewById(R.id.tv_tenbac);
            tvTuso = itemView.findViewById(R.id.tv_tusokw);
            tvDenso = itemView.findViewById(R.id.tv_densokw);
            tvDongia = itemView.findViewById(R.id.tv_dongia);
        }
    }
}
