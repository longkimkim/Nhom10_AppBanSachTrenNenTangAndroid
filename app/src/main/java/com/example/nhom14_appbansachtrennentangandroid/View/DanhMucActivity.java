package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.adapter.SanPhamDanhMucAdapter;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhMuc;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhMucActivity extends AppCompatActivity {
    ArrayList<SanPham> listDM;
    SanPhamDanhMucAdapter sanPhamDanhMucAdapter;
    RecyclerView rcDanhMuc;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    ImageView img_back;
    String maDanhMuc;
    TextView tv_TenDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        rcDanhMuc = findViewById(R.id.rcDanhMuc);
        img_back = findViewById(R.id.img_back);
        tv_TenDM = findViewById(R.id.tv_TenDM);

        Intent intent = getIntent();
        maDanhMuc = intent.getStringExtra("maDanhMuc");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplication(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rcDanhMuc.setLayoutManager(gridLayoutManager);

        listDM= new ArrayList<>();
        sanPhamDanhMucAdapter = new SanPhamDanhMucAdapter(listDM, this::onItemClick, getApplication());
        rcDanhMuc.setAdapter(sanPhamDanhMucAdapter);
        getSPDanhMuc();
        NetworkChangeListener networkChangeListener = new NetworkChangeListener();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getSPDanhMuc() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("sanpham");
        DatabaseReference databaseReference = firebaseDatabase.getReference("DanhMuc");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    if(sanPham.getMaDanhMuc().equals(maDanhMuc)){
                        listDM.add(sanPham);
                    }
                }
                sanPhamDanhMucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(),"Get Book Fail!",Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DanhMuc danhMuc = dataSnapshot.getValue(DanhMuc.class);
                    if(danhMuc.getMaDanhMuc().equals(maDanhMuc)){
                        tv_TenDM.setText(danhMuc.getTenDanhMuc());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(),"Get Book Fail!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    public void onItemClick(SanPham sanPham) {
        Intent intent = new Intent(getApplication(), ChiTietSPActivity.class);
        intent.putExtra("maSP", sanPham.getIdSp()+"");
        startActivity(intent);
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