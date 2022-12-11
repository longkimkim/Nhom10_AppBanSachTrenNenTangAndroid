package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietDonHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.GioHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.PostDanhGiaActivity;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.example.nhom14_appbansachtrennentangandroid.model.ThongBao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThongBaoAdapter   extends RecyclerView.Adapter<ThongBaoAdapter.Holder> {
        List<DonHang> donHangList;
        Context context;
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public ThongBaoAdapter(List<DonHang> donHangList, Context context) {
            this.donHangList = donHangList;
            this.context = context;
            }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.item_thongbao, parent, false);
            return new Holder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            DonHang donHang=donHangList.get(position);
            init(donHang, holder);
            holder.ln_thongbao.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            chitiet(donHang.getId());
            }
            });

            }



    @Override
    public int getItemCount() {
            return donHangList.size();
            }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_anh;

        TextView tv_idDH, tv_thongbao,tv_time;
        LinearLayout ln_thongbao;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_anh=itemView.findViewById(R.id.anhdonhang);
            ln_thongbao=itemView.findViewById(R.id.item_thongbaomoi);
            tv_idDH=itemView.findViewById(R.id.tv_tendonhang);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_thongbao=itemView.findViewById(R.id.tv_thongbaofragment);
        }
    }



        private void chitiet(String id){
            Intent intent=new Intent(context, ChiTietDonHangActivity.class);
            intent.putExtra("id",id);
            context.startActivity(intent);
        }



        private void init(DonHang donHang, Holder holder){

            holder.tv_idDH.setText(donHang.getId());
            holder.tv_thongbao.setText(donHang.getTrangThai());
            holder.tv_time.setText(donHang.getNgayTao());
            if(donHang.getTrangThai().equals("Chờ xác nhận")){
                Glide.with(context).load(R.drawable.cho).error(R.drawable.avatardefault).into(holder.img_anh);

            }
            else if(donHang.getTrangThai().equals("Đã nhận")){
                Glide.with(context).load(R.drawable.dacnhan).error(R.drawable.avatardefault).into(holder.img_anh);

            }
            else if(donHang.getTrangThai().equals("Đang giao")){
                Glide.with(context).load(R.drawable.ic_giaohang).error(R.drawable.avatardefault).into(holder.img_anh);

            }
            else if(donHang.getTrangThai().equals("Hủy")){
                Glide.with(context).load(R.drawable.huy).error(R.drawable.avatardefault).into(holder.img_anh);

            }

        }
    }