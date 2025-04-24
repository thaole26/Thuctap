package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.BangGiaApDung;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BangGiaApDungAdapter extends RecyclerView.Adapter<BangGiaApDungAdapter.ViewHolder> {

    private Context context;
    private List<BangGiaApDung> bangGiaList;
    private OnItemClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public interface OnItemClickListener {
        void onItemClick(BangGiaApDung bangGia);
    }

    public BangGiaApDungAdapter(Context context, List<BangGiaApDung> bangGiaList, OnItemClickListener listener) {
        this.context = context;
        this.bangGiaList = bangGiaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bang_gia, parent, false); // Sử dụng item_bang_gia.xml
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BangGiaApDung bangGia = bangGiaList.get(position);
        holder.tvItemTenBangGia.setText(bangGia.getTen_banggia());

        String hieuLuc = "Hiệu lực: " + dateFormat.format(bangGia.getNgay_apdung());
        if (bangGia.getNgay_ketthuc() != null) {
            hieuLuc += " đến " + dateFormat.format(bangGia.getNgay_ketthuc());
        } else {
            hieuLuc += " đến (Hiện hành)";
        }
        holder.tvItemNgayHieuLucBG.setText(hieuLuc);

        String trangThaiText = bangGia.getTrangthai() == 1 ? "Trạng thái: Đang sử dụng" : "Trạng thái: Đã ngừng";
        holder.tvItemTrangThaiBG.setText(trangThaiText);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(bangGia));
    }

    @Override
    public int getItemCount() {
        return bangGiaList.size();
    }

    public void updateList(List<BangGiaApDung> newList) {
        bangGiaList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemTenBangGia;
        public TextView tvItemNgayHieuLucBG;
        public TextView tvItemTrangThaiBG;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTenBangGia = itemView.findViewById(R.id.tvItemTenBangGia);
            tvItemNgayHieuLucBG = itemView.findViewById(R.id.tvItemNgayHieuLucBG);
            tvItemTrangThaiBG = itemView.findViewById(R.id.tvItemTrangThaiBG);
        }
    }
}