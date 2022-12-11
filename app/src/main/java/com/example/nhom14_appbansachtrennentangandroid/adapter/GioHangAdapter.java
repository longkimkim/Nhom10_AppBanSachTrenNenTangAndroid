package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietSPActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.GioHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.MainActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.fragment.HomeFragment;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;
    List<GioHang> gioHangList;
    DatabaseReference db;
    FirebaseDatabase firebaseDatabase;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        if(gioHangList != null){
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
        View view = layoutInflater.inflate(R.layout.item_giohang, parent,false);
        TextView tvName, tvTangSL,tvGiamSL,tvSoLuong, tvPrice;
        ImageView imgPicture;
        tvName = view.findViewById(R.id.tvNameProduct_GH);
        tvPrice = view.findViewById(R.id.tvPrice_GH);
        tvTangSL = view.findViewById(R.id.tvTangSL_GH);
        tvGiamSL = view.findViewById(R.id.tvGiamSL_GH);
        tvSoLuong = view.findViewById(R.id.tvSoLuong_GH);
        imgPicture = view.findViewById(R.id.imgPictureProduct_GH);
        tvName.setMaxLines(2);
        tvName.setEllipsize(TextUtils.TruncateAt.END);
        GioHang gioHang = gioHangList.get(position);
        tvName.setText(gioHang.getTenSP());
        tvPrice.setText(gioHang.getDonGia()+"");
        Glide.with(context).load(gioHang.getImg()).error(R.drawable.avatardefault).into(imgPicture);
        tvSoLuong.setText(gioHang.getSoluong()+"");
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tvGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(tvSoLuong.getText().toString()) == 1) {
                    androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("")
                            .setMessage("Bạn có chắc chắc chắn muốn xóa sản phẩm này")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gioHangList.remove(position);
                                    db.child("giohang").child(user.getUid()).child(gioHang.getIdsp()).removeValue();
                                    notifyDataSetChanged();
                                    GioHangActivity.tongtien();
                                    GioHangActivity.SoLuongGioHang();
                                    HomeFragment.getSoLuongGiohang();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create();
                    alertDialog.show();
                    return;
                }
                int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString())-1;
                tvSoLuong.setText(soluongmoi+"");
                MainActivity.listGioHang.get(position).setSoluong(soluongmoi);
                db.child("giohang").child(user.getUid()).child(gioHang.getIdsp()).child("soluong").setValue(soluongmoi);
                GioHangActivity.tongtien();
                GioHangActivity.SoLuongGioHang();
                HomeFragment.getSoLuongGiohang();
            }
        });
        tvTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(tvSoLuong.getText().toString()) + 1;
                tvSoLuong.setText(soluongmoi+"");
                MainActivity.listGioHang.get(position).setSoluong(soluongmoi);
                notifyDataSetChanged();
                db.child("giohang").child(user.getUid()).child(gioHang.getIdsp()).child("soluong").setValue(soluongmoi);
                GioHangActivity.tongtien();
                GioHangActivity.SoLuongGioHang();
                HomeFragment.getSoLuongGiohang();
            }
        });
        return view;
    }
}
