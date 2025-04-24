package com.example.myapplication.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.KhachHang;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private List<KhachHang> originalList;
    private List<KhachHang> filteredList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(KhachHang khachHang);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                Log.d("Adapter", "Item clicked: " + kh.getMakh()); // debug
                listener.onItemClick(kh);
            }
        });
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

    public void updateList(List<KhachHang> newList) {
        originalList = newList;
        filteredList = new ArrayList<>(newList); // if you use filtering
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
