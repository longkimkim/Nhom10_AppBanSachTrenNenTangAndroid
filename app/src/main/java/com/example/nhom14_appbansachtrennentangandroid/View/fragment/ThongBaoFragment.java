package com.example.nhom14_appbansachtrennentangandroid.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietDonHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.ChiTietSPActivity;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DSDonHangAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.SanPhamAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.ThongBaoAdapter;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.example.nhom14_appbansachtrennentangandroid.model.ThongBao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThongBaoFragment extends Fragment  {
    View mView;
    List<DonHang> donHangList;
    RecyclerView rcThongBao;
    ThongBaoAdapter thongBaoAdapter;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    RelativeLayout lnchuaDH;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        rcThongBao=mView.findViewById(R.id.rcThongBao);
        lnchuaDH=mView.findViewById(R.id.ln_thongbao);
        donHangList=new ArrayList<>();

        rcThongBao.setLayoutManager(new LinearLayoutManager(getActivity()));
        thongBaoAdapter=new ThongBaoAdapter(donHangList, getContext());
        rcThongBao.setAdapter(thongBaoAdapter);
        layDonHang();
        return  mView;
    }


    private void layDonHang(){
        reference.child("donhang").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DonHang donHang=dataSnapshot.getValue(DonHang.class);
                        donHangList.add(donHang);
                }
                Collections.reverse(donHangList);
                if(donHangList.size()>0){
                    thongBaoAdapter.notifyDataSetChanged();
                    rcThongBao.setVisibility(View.VISIBLE);
                    lnchuaDH.setVisibility(View.GONE);
                }else{
                    rcThongBao.setVisibility(View.GONE);
                    lnchuaDH.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}