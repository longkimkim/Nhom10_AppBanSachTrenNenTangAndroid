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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DSDonHangAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityTimKiemDonBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimKiemDonActivity extends AppCompatActivity {

    ActivityTimKiemDonBinding binding;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    List<String> sanPhamList=new ArrayList<>();
    List<DonHang> donHangList=new ArrayList<>();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    DSDonHangAdapter adapter;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_don);
        binding= DataBindingUtil.setContentView(TimKiemDonActivity.this, R.layout.activity_tim_kiem_don);

        setSupportActionBar(binding.toolbarTk);
        getSupportActionBar().setTitle("Tìm kiếm đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSP();



        binding.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimKiemDonActivity.this, MainActivity.class);
                intent.putExtra("trang", 1);
                startActivity(intent);
            }
        });

        binding.recDonhang.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter=new DSDonHangAdapter(donHangList, getApplication());
        binding.recDonhang.setAdapter(adapter);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.select_dialog_item, sanPhamList);
        binding.tvTimKiem.setAdapter(arrayAdapter);

        binding.imgTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layDonHang();
            }
        });

    }

   private void getSP(){
       reference.child("sanpham").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               sanPhamList.clear();
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                   sanPhamList.add(sanPham.getTenSP());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

   }


    private void layDonHang(){
        reference.child("donhang").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DonHang donHang=dataSnapshot.getValue(DonHang.class);
                    List<GioHang> gioHangList=donHang.getGioHangList();
                    for(GioHang gioHang: gioHangList){
                        if(gioHang.getTenSP().toUpperCase().contains(binding.tvTimKiem.getText().toString().toUpperCase())){
                            donHangList.add(donHang);
                        }
                    }
                }
                if(donHangList.size()>0){
                    adapter.notifyDataSetChanged();
                    binding.recDonhang.setVisibility(View.VISIBLE);
                    binding.lnChuaTim.setVisibility(View.GONE);
                    binding.lnChuaDH.setVisibility(View.GONE);
                }else{
                    binding.recDonhang.setVisibility(View.GONE);
                    binding.lnChuaTim.setVisibility(View.GONE);
                    binding.lnChuaDH.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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