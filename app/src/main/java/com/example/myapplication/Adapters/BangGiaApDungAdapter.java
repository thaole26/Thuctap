package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.QLGiadienActivity; // Import Activity quản lý giá điện
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
    private int selectedPosition = RecyclerView.NO_POSITION;

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
                .inflate(R.layout.item_bang_gia, parent, false);
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

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("position", selectedPosition+" "+position);
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            listener.onItemClick(bangGia);

            // Chuyển sang QLGiaDienActivity khi nhấn vào item
            Intent intent = new Intent(context, QLGiadienActivity.class);
            intent.putExtra("id_banggia", bangGia.getId_banggia()); // Truyền ID bảng giá
            context.startActivity(intent);
        });
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

    public int getSelectedItemPosition() {
        return selectedPosition;
    }

    public int getSelectedItemId() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return bangGiaList.get(selectedPosition).getId_banggia();
        }
        return -1;
    }

    public void clearSelectedItem() {
        int oldPosition = selectedPosition;
        selectedPosition = RecyclerView.NO_POSITION;
        notifyItemChanged(oldPosition);
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(selectedPosition);
    }
}