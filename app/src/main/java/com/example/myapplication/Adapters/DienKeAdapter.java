package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.DienKe;
import com.example.myapplication.R;
import java.util.List;

public class DienKeAdapter extends RecyclerView.Adapter<DienKeAdapter.DienKeViewHolder> {

    private List<DienKe> dienKeList;

    public DienKeAdapter(List<DienKe> dienKeList) {
        this.dienKeList = dienKeList;
    }

    public interface OnItemClickListener {
        void onItemClick(DienKe dienKe);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DienKeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienke, parent, false);
        return new DienKeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DienKeViewHolder holder, int position) {
        DienKe dk = dienKeList.get(position);
        holder.tvMaDK.setText("Mã ĐK: " + dk.getMadk());
        holder.tvMaKH.setText("Mã KH: " + dk.getMakh());
        holder.tvNgaySX.setText("Ngày SX: " + dk.getNgaysx());
        holder.tvNgayLap.setText("Ngày Lắp: " + dk.getNgaylap());
        holder.tvMota.setText("Mô tả: " + dk.getMota());
        holder.tvTrangThai.setText("Trạng thái: " + (dk.getTrangthai() == 1 ? "Đang hoạt động" : "Ngưng hoạt động"));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(dk);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dienKeList.size();
    }

    public void updateList(List<DienKe> newList) {
        this.dienKeList = newList;
        notifyDataSetChanged();
    }

    public static class DienKeViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaDK, tvMaKH, tvNgaySX, tvNgayLap, tvMota, tvTrangThai;

        public DienKeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDK = itemView.findViewById(R.id.tvMaDienKe);
            tvMaKH = itemView.findViewById(R.id.tvMaKH);
            tvNgaySX = itemView.findViewById(R.id.tvNgaySX);
            tvNgayLap = itemView.findViewById(R.id.tvNgayLap);
            tvMota = itemView.findViewById(R.id.tvMota);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
