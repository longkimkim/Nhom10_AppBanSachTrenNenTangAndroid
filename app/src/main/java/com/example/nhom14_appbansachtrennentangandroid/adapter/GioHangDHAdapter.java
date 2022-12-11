package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietSPActivity;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;

import java.util.List;

public class GioHangDHAdapter extends RecyclerView.Adapter<GioHangDHAdapter.Holder> {

    Context context;
    List<GioHang> gioHangList;

    public GioHangDHAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_dh,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        GioHang gioHang=gioHangList.get(position);
        Glide.with(context).load(gioHang.getImg()).error(R.drawable.anhnen).into(holder.imgPictureProduct_DH);
        holder.tvSoLuong_DH.setText("x"+ gioHang.getSoluong());
        holder.tvPrice_DH.setText(gioHang.getDonGia()+"đ");
        holder.tvNameProduct_DH.setText(gioHang.getTenSP());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("maSP", gioHang.getIdsp());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imgPictureProduct_DH;
        TextView tvNameProduct_DH,tvPrice_DH,tvSoLuong_DH;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imgPictureProduct_DH=itemView.findViewById(R.id.imgPictureProduct_DH);
            tvNameProduct_DH=itemView.findViewById(R.id.tvNameProduct_DH);
            tvPrice_DH=itemView.findViewById(R.id.tvPrice_DH);
            tvSoLuong_DH=itemView.findViewById(R.id.tvSoLuong_DH);
        }
    }
}
