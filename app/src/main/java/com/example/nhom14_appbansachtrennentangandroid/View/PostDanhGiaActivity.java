package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.adapter.PostDanhGiaAdapter;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityPostDanhGiaBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhGia;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class PostDanhGiaActivity extends AppCompatActivity {

    ActivityPostDanhGiaBinding binding;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    PostDanhGiaAdapter adapter;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    List<GioHang> gioHangList=new LinkedList<>();
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(PostDanhGiaActivity.this, R.layout.activity_post_danh_gia);


        binding.recDanhGia.getAdapter();
        setSupportActionBar(binding.toolbarSp);
        getSupportActionBar().setTitle("");

        binding.imgQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        if(id==null){
            AlertDialog ad = new AlertDialog.Builder(PostDanhGiaActivity.this).create();
            ad.setTitle("Thông báo");
            String msg = String.format("Đơn hàng này không tồn tại");
            ad.setMessage(msg);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ad.show();
            onBackPressed();
        }

        reference.child("donhang").child(user.getUid()).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonHang donHang=snapshot.getValue(DonHang.class);
                gioHangList=donHang.getGioHangList();
                adapter=new PostDanhGiaAdapter(gioHangList,getApplicationContext(), id);
                binding.recDanhGia.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                binding.recDanhGia.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getDanhGiaList().size()>0){
                    for(DanhGia danhGia: adapter.getDanhGiaList()){
                        if(danhGia.getSao()>0){
                            reference.child("danhgia").child(danhGia.getIddanggia()).setValue(danhGia);
                            reference.child("donhang").child(user.getUid()).child(id).child("trangThai").setValue("Đã đánh giá");
                            startActivity(new Intent(PostDanhGiaActivity.this, DonHangActivity.class));
                        }
                    }
                }else{
                    AlertDialog ad = new AlertDialog.Builder(PostDanhGiaActivity.this).create();
                    ad.setTitle("Thông báo");
                    String msg = String.format("Bạn chưa đánh giá!");
                    ad.setMessage(msg);
                    ad.setIcon(android.R.drawable.ic_dialog_info);
                    ad.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    ad.show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}