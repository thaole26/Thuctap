package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private final List<KhachHang> originalList;
    private final List<KhachHang> filteredList;

    public KhachHangAdapter(List<KhachHang> list) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang kh = filteredList.get(position);
        holder.makh.setText("Mã KH: " + kh.getMakh());
        holder.tenkh.setText("Tên KH: " + kh.getTenkh());
        holder.diachi.setText("Địa chỉ: " + kh.getDiachi());
        holder.sdt.setText("SĐT: " + kh.getDt());
        holder.cmnd.setText("CMND: " + kh.getCmnd());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            text = text.toLowerCase();
            for (KhachHang kh : originalList) {
                if (kh.getMakh().toLowerCase().contains(text) || kh.getTenkh().toLowerCase().contains(text)) {
                    filteredList.add(kh);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView makh, tenkh, diachi, sdt, cmnd;

        public ViewHolder(View itemView) {
            super(itemView);
            makh = itemView.findViewById(R.id.tvMaKH);
            tenkh = itemView.findViewById(R.id.tvTenKH);
            diachi = itemView.findViewById(R.id.tvDiaChi);
            sdt = itemView.findViewById(R.id.tvDT);
            cmnd = itemView.findViewById(R.id.tvCMND);
        }
    }
}
