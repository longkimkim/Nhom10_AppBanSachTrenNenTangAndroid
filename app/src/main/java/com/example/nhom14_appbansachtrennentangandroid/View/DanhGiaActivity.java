package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DanhGiaAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityDanhGiaBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhGia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DanhGiaActivity extends AppCompatActivity {
    ActivityDanhGiaBinding binding;
    String maSP="";
    DanhGiaAdapter danhGiaAdapter;
    List<DanhGia> danhGiaList;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(DanhGiaActivity.this, R.layout.activity_danh_gia);

        setSupportActionBar(binding.toolbarSp);
        getSupportActionBar().setTitle("");

        Intent intent=getIntent();
        maSP=intent.getStringExtra("maSP");

        binding.recDanhGia.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        danhGiaList=new ArrayList<>();
        reference.child("danhgia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhGiaList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DanhGia danhGia= dataSnapshot.getValue(DanhGia.class);
                    if(danhGia.getIdSp().equals(maSP)){
                        danhGiaList.add(danhGia);
                    }
                }
                danhGiaAdapter=new DanhGiaAdapter(danhGiaList, getApplicationContext());
                binding.recDanhGia.setAdapter(danhGiaAdapter);
                danhGiaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.imgQl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(DanhGiaActivity.this, ChiTietSPActivity.class);
                intent1.putExtra("maSP", maSP);
                startActivity(intent1);
                finish();
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