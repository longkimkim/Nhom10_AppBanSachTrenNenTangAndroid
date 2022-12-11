package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangAdapter extends BaseAdapter {
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;
    List<GioHang> gioHangList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public DonHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }
    @Override
    public int getCount() {
        if(gioHangList != null) {
            return gioHangList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return gioHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_thanh_toan, parent,false);
        TextView tvName,tvSoLuong, tvDonGia;
        ImageView imgPicture;
        tvName = view.findViewById(R.id.tvNameProduct_DH);
        tvDonGia = view.findViewById(R.id.tvPrice_DH);
        tvSoLuong = view.findViewById(R.id.tvSoLuong_DH);
        imgPicture = view.findViewById(R.id.imgPictureProduct_DH);
        GioHang giohang = gioHangList.get(position);
        tvName.setText(giohang.getTenSP());
        tvDonGia.setText(formatPrice.format(giohang.getDonGia()) +"");
        Glide.with(context).load(giohang.getImg()).error(R.drawable.avatardefault).into(imgPicture);
        tvSoLuong.setText(giohang.getSoluong()+"");
        return view;
    }
}
