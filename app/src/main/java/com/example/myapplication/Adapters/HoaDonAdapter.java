package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.HoaDon;
import com.example.myapplication.R;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {

    private List<HoaDon> hoaDonList;

    public HoaDonAdapter(List<HoaDon> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hd = hoaDonList.get(position);
        holder.tvMahd.setText("Mã HĐ: " + hd.getMahd());
        holder.tvMadk.setText("Mã điện kế: " + hd.getMadk());
        holder.tvKy.setText("Kỳ: " + hd.getKy());
        holder.tvChiso.setText("Chỉ số: " + hd.getChisodau() + " → " + hd.getChisocuoi());
        holder.tvTongtien.setText("Tổng tiền: " + hd.getTongthanhtien());
        holder.tvNgaylap.setText("Ngày lập: " + hd.getNgaylaphd());
        holder.tvTinhtrang.setText("Tình trạng: " + (hd.getTinhtrang() == 1 ? "Đã thanh toán" : "Chưa thanh toán"));
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public void updateList(List<HoaDon> newList) {
        this.hoaDonList = newList;
        notifyDataSetChanged();
    }

    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMahd, tvMadk, tvKy, tvChiso, tvTongtien, tvNgaylap, tvTinhtrang;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMahd = itemView.findViewById(R.id.tvMahd);
            tvMadk = itemView.findViewById(R.id.tvMadk);
            tvKy = itemView.findViewById(R.id.tvKy);
            tvChiso = itemView.findViewById(R.id.tvChiso);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
            tvNgaylap = itemView.findViewById(R.id.tvNgaylap);
            tvTinhtrang = itemView.findViewById(R.id.tvTinhtrang);
        }
    }
}
