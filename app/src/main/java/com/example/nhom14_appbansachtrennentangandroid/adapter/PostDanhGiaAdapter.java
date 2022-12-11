package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietSPActivity;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhGia;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostDanhGiaAdapter extends RecyclerView.Adapter<PostDanhGiaAdapter.Holder> {
    List<GioHang>gioHangList;
    Context context;
    String idDH;
    List<DanhGia> danhGiaList=new ArrayList<>();

    public List<DanhGia> getDanhGiaList() {
        return danhGiaList;
    }

    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public PostDanhGiaAdapter(List<GioHang> gioHangList, Context context, String idDH) {
        this.gioHangList = gioHangList;
        this.context = context;
        this.idDH = idDH;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_gia, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        GioHang gioHang=gioHangList.get(position);
        int i=position;
        Glide.with(context).load(gioHang.getImg()).error(R.drawable.avatardefault).into(holder.img_anh);
        holder.tv_tenSp.setText(gioHang.getTenSP());
        List<Integer> so=new ArrayList<>();
        so.add(0);
        holder.img_sao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so.set(0,1);
                hien(so.get(0),holder );
                layDanhGia(holder, gioHang, i, so.get(0));
            }
        });
        holder.img_sao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so.set(0,2);
                hien(so.get(0),holder );
                layDanhGia(holder, gioHang, i, so.get(0));
            }
        });
        holder.img_sao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so.set(0,3);
                hien(so.get(0),holder );
                layDanhGia(holder, gioHang, i, so.get(0));
            }
        });
        holder.img_sao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so.set(0,4);
                hien(so.get(0),holder );
                layDanhGia(holder, gioHang, i, so.get(0));
            }
        });
        holder.img_sao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so.set(0,5);
                hien(so.get(0),holder );
                layDanhGia(holder, gioHang, i, so.get(0));
            }
        });

        holder.et_noidung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               layDanhGia(holder, gioHang, i, so.get(0));
            }
        });

        DanhGia danhGia=new DanhGia("","nd", gioHang.getIdsp(), 0, user.getUid(),"date"  );
        danhGiaList.add(danhGia);
    }

    private void layDanhGia(Holder holder, GioHang gioHang, int i, int so) {
        String nd=holder.et_noidung.getText().toString();
        String id=reference.child("danhgia").push().getKey();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());
        DanhGia danhGia=new DanhGia(id,nd, gioHang.getIdsp(), so, user.getUid(),date  );
        danhGiaList.set(i, danhGia);
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_anh,img_saovang1, img_saovang2, img_saovang3,img_saovang4, img_saovang5;
        ImageView img_sao1,img_sao2, img_sao3,img_sao4,img_sao5;
        TextView tv_tenSp;
        EditText et_noidung;
        public Holder(@NonNull View itemView) {
            super(itemView);
            img_anh=itemView.findViewById(R.id.img_anh);
            img_saovang1=itemView.findViewById(R.id.img_saovang1);
            img_saovang2=itemView.findViewById(R.id.img_saovang2);
            img_saovang3=itemView.findViewById(R.id.img_saovang3);
            img_saovang4=itemView.findViewById(R.id.img_saovang4);
            img_saovang5=itemView.findViewById(R.id.img_saovang5);
            tv_tenSp=itemView.findViewById(R.id.tv_tenSp);
            et_noidung=itemView.findViewById(R.id.et_noidung);
            img_sao1=itemView.findViewById(R.id.img_sao1);
            img_sao2=itemView.findViewById(R.id.img_sao2);
            img_sao3=itemView.findViewById(R.id.img_sao3);
            img_sao4=itemView.findViewById(R.id.img_sao4);
            img_sao5=itemView.findViewById(R.id.img_sao5);
        }
    }

    private void hien(int so, Holder holder){
        switch (so){
            case 5:
                holder.img_saovang1.setVisibility(View.VISIBLE);
                holder.img_saovang2.setVisibility(View.VISIBLE);
                holder.img_saovang3.setVisibility(View.VISIBLE);
                holder.img_saovang4.setVisibility(View.VISIBLE);
                holder.img_saovang5.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.img_saovang1.setVisibility(View.VISIBLE);
                holder.img_saovang2.setVisibility(View.VISIBLE);
                holder.img_saovang3.setVisibility(View.VISIBLE);
                holder.img_saovang4.setVisibility(View.VISIBLE);
                holder.img_saovang5.setVisibility(View.GONE);
                break;
            case 3:
                holder.img_saovang1.setVisibility(View.VISIBLE);
                holder.img_saovang2.setVisibility(View.VISIBLE);
                holder.img_saovang3.setVisibility(View.VISIBLE);
                holder.img_saovang4.setVisibility(View.GONE);
                holder.img_saovang5.setVisibility(View.GONE);
                break;
            case 2:
                holder.img_saovang1.setVisibility(View.VISIBLE);
                holder.img_saovang2.setVisibility(View.VISIBLE);
                holder.img_saovang3.setVisibility(View.GONE);
                holder.img_saovang4.setVisibility(View.GONE);
                holder.img_saovang5.setVisibility(View.GONE);
                break;
            case 1:
                holder.img_saovang1.setVisibility(View.VISIBLE);
                holder.img_saovang2.setVisibility(View.GONE);
                holder.img_saovang3.setVisibility(View.GONE);
                holder.img_saovang4.setVisibility(View.GONE);
                holder.img_saovang5.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
