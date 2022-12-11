package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietDonHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.GioHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.PostDanhGiaActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.fragment.HomeFragment;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.ThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DSDonHangAdapter extends RecyclerView.Adapter<DSDonHangAdapter.Holder> {
    List<DonHang> donHangList;
    Context context;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public DSDonHangAdapter(List<DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dh_dang_giao, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DonHang donHang=donHangList.get(position);
        init(donHang, holder);
        holder.tv_xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chitiet(donHang.getId());
            }
        });

        if(tinhNgay(donHang.getNgayTao())&& donHang.getTrangThai().equals("Chờ xác nhận")){
            reference.child("donhang").child(user.getUid()).child(donHang.getId()).child("trangThai")
                    .setValue("Đang giao").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        String idTb= reference.child("thongbao").child(user.getUid()).push().getKey();
                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
                        String date = df.format(Calendar.getInstance().getTime());
                        ThongBao thongBao=new ThongBao(idTb, donHang.getId(), "Đang giao",date );
                        reference.child("thongbao").child(user.getUid()).child(thongBao.getIdTB()).setValue(thongBao);
                    }
                }
            });
        }

        holder.btn_danhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("donhang").child(user.getUid()).child(donHang.getId()).child("trangThai")
                        .setValue("Đã nhận").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            String idTb= reference.child("thongbao").child(user.getUid()).push().getKey();
                            DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
                            String date = df.format(Calendar.getInstance().getTime());
                            ThongBao thongBao=new ThongBao(idTb, donHang.getId(), "Đã nhận",date );
                            reference.child("thongbao").child(user.getUid()).child(thongBao.getIdTB()).setValue(thongBao);
                        }
                    }
                });
            }
        });
        holder.btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                        .setTitle("")
                        .setMessage("Bạn có chắc chắc chắn muốn hủy đơn hàng này không!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reference.child("donhang").child(user.getUid()).child(donHang.getId()).child("trangThai")
                                        .setValue("Đã hủy").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            String idTb= reference.child("thongbao").child(user.getUid()).push().getKey();
                                            DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
                                            String date = df.format(Calendar.getInstance().getTime());
                                            ThongBao thongBao=new ThongBao(idTb, donHang.getId(), "Đã hủy",date );
                                            reference.child("thongbao").child(user.getUid()).child(thongBao.getIdTB()).setValue(thongBao);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.show();

            }
        });

        holder.btn_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context, PostDanhGiaActivity.class);
                intent1.putExtra("id", donHang.getId());
                context.startActivity(intent1);

            }
        });

        holder.btn_mualai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muaLai(donHang.getGioHangList());

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        View gach2;
        TextView tv_tenSP, tv_sl,tv_ngay, tv_gia,tv_xem,tv_tongsl, tv_thanhtien;
        Button btn_danhgia,btn_danhan, btn_mualai, btn_huy;
        RelativeLayout rl_cho, rl_da_huy, rl_da_nhan,rl_dang_giao, rl_da_danh_gia;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_anh=itemView.findViewById(R.id.img_anh);
            gach2=itemView.findViewById(R.id.gach2);
            tv_tenSP=itemView.findViewById(R.id.tv_tenSP);
            tv_sl=itemView.findViewById(R.id.tv_sl);
            tv_thanhtien=itemView.findViewById(R.id.tv_thanhtien);
            tv_gia=itemView.findViewById(R.id.tv_gia);
            tv_ngay=itemView.findViewById(R.id.tv_ngay);
            tv_xem=itemView.findViewById(R.id.tv_xem);
            tv_tongsl=itemView.findViewById(R.id.tv_tongsl);
            btn_danhan=itemView.findViewById(R.id.btn_danhan);
            btn_danhgia=itemView.findViewById(R.id.btn_danhgia);
            btn_huy=itemView.findViewById(R.id.btn_huy);
            btn_mualai=itemView.findViewById(R.id.btn_mualai);
            rl_cho=itemView.findViewById(R.id.rl_cho);
            rl_da_huy=itemView.findViewById(R.id.rl_da_huy);
            rl_da_nhan=itemView.findViewById(R.id.rl_da_nhan);
            rl_dang_giao=itemView.findViewById(R.id.rl_dang_giao);
            rl_da_danh_gia=itemView.findViewById(R.id.rl_da_danh_gia);
        }
    }







    private boolean tinhNgay(String ngayTao){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Calendar calendar = Calendar.getInstance();
            Date ngay=df.parse(ngayTao);
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.set(ngay.getYear(), ngay.getMonth(),ngay.getDate());
            int day=calendar.get(Calendar.DAY_OF_YEAR);
            if(dayOfYear-day>=3){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void chitiet(String id){
        Intent intent=new Intent(context, ChiTietDonHangActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }


    private void muaLai(List<GioHang> gioHangList) {
        for(GioHang gioHang1: gioHangList){
            reference.child("giohang").child(user.getUid()).child(gioHang1.getIdsp()).setValue(gioHang1);
        }
        context.startActivity(new Intent(context, GioHangActivity.class));
    }



    private void init(DonHang donHang, Holder holder){
        GioHang gioHang=donHang.getGioHangList().get(0);
        if(gioHang!=null){
            Glide.with(context).load(gioHang.getImg()).error(R.drawable.ic_baseline_hide_image_24).into(holder.img_anh);
            holder.tv_tenSP.setText(gioHang.getTenSP());
            holder.tv_sl.setText("x"+ gioHang.getSoluong());
            holder.tv_gia.setText(gioHang.getDonGia()+"đ");
        }
        int tongsl=0;

        for(GioHang gioHang1: donHang.getGioHangList()){
            tongsl+=gioHang1.getSoluong();
        }

        holder.tv_ngay.setText(donHang.getNgayTao());
        holder.tv_tongsl.setText(tongsl+" sản phẩm");
        holder.tv_thanhtien.setText(donHang.getTongTien()+"đ");
        if(donHang.getGioHangList().size()>1){
            holder.tv_xem.setVisibility(View.VISIBLE);
            holder.gach2.setVisibility(View.VISIBLE);
        }
        else {
            holder.tv_xem.setVisibility(View.GONE);
            holder.gach2.setVisibility(View.GONE);
        }

        if(donHang.getTrangThai().equals("Chờ xác nhận")){
            holder.rl_cho.setVisibility(View.VISIBLE);
            holder.rl_dang_giao.setVisibility(View.GONE);
            holder.rl_da_nhan.setVisibility(View.GONE);
            holder.rl_da_huy.setVisibility(View.GONE);
            holder.rl_da_danh_gia.setVisibility(View.GONE);
        }else if(donHang.getTrangThai().equals("Đã nhận")){
            holder.rl_cho.setVisibility(View.GONE);
            holder.rl_dang_giao.setVisibility(View.GONE);
            holder.rl_da_nhan.setVisibility(View.VISIBLE);
            holder.rl_da_huy.setVisibility(View.GONE);
            holder.rl_da_danh_gia.setVisibility(View.GONE);
        }else if(donHang.getTrangThai().equals("Đang giao")){
            holder.rl_cho.setVisibility(View.GONE);
            holder.rl_dang_giao.setVisibility(View.VISIBLE);
            holder.rl_da_nhan.setVisibility(View.GONE);
            holder.rl_da_huy.setVisibility(View.GONE);
            holder.rl_da_danh_gia.setVisibility(View.GONE);
        }else if(donHang.getTrangThai().equals("Đã hủy")){
            holder.rl_cho.setVisibility(View.GONE);
            holder.rl_dang_giao.setVisibility(View.GONE);
            holder.rl_da_nhan.setVisibility(View.GONE);
            holder.rl_da_huy.setVisibility(View.VISIBLE);
            holder.rl_da_danh_gia.setVisibility(View.GONE);
        }else {
            holder.rl_cho.setVisibility(View.GONE);
            holder.rl_dang_giao.setVisibility(View.GONE);
            holder.rl_da_nhan.setVisibility(View.GONE);
            holder.rl_da_huy.setVisibility(View.GONE);
            holder.rl_da_danh_gia.setVisibility(View.VISIBLE);
        }
    }
}
