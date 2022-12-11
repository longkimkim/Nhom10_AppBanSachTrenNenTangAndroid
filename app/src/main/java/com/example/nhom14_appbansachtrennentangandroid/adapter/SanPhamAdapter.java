package com.example.nhom14_appbansachtrennentangandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class SanPhamAdapter  extends  RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder>{
    List<SanPham> list;
    private  ItemClickListener clickListener;

    Context context;
    public  SanPhamAdapter(List<SanPham> list,ItemClickListener clickListener, Context context){
        this.list=list;
        this.context=context;
        this.clickListener= clickListener;
    }
    @NonNull
    @NotNull
    @Override
    public SanPhamAdapter.SanPhamViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SanPhamAdapter.SanPhamViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if(sanPham == null){
            return;
        }
        holder.item_ten.setText(sanPham.getTenSP());
        holder.item_gia.setText(sanPham.getDonGia()+"đ");
        holder.item_sao.setText(sanPham.getSaoDanhGia()+"");
        Glide.with(context).load(sanPham.getImg()).error(R.drawable.avatardefault).into(holder.item_anh);
        holder.item_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(list.get(position));
            }
        });
    }
    public  interface ItemClickListener{
        public void onItemClick(SanPham  sanPham);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class SanPhamViewHolder extends RecyclerView.ViewHolder{
        TextView item_ten,item_gia,item_sao;
        ImageView item_anh;
        LinearLayout item_sach;

        public  SanPhamViewHolder(View view){
            super(view);
            item_ten=view.findViewById(R.id.item_tensach);
            item_gia=view.findViewById(R.id.item_gia);
            item_sao=view.findViewById(R.id.item_sao);
            item_anh=view.findViewById(R.id.item_anhsach);
            item_sach=view.findViewById(R.id.id_ln_iem_sach) ;
        }
    }

}
