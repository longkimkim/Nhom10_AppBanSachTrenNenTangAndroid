package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DonHangAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.GioHangDHAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityChiTietDonHangBinding;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityChiTietDonHangBindingImpl;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.ThongTinNhanHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangActivity extends AppCompatActivity {

    ActivityChiTietDonHangBinding binding;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String id="";
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    List<GioHang> gioHangList=new ArrayList<>();
    GioHangDHAdapter adapter;
    ThongTinNhanHang thongTinNhanHang=new ThongTinNhanHang();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(ChiTietDonHangActivity.this, R.layout.activity_chi_tiet_don_hang);

        setSupportActionBar(binding.toolbarSp);
        getSupportActionBar().setTitle("Chi tiết đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        if(id==null){
            AlertDialog ad = new AlertDialog.Builder(ChiTietDonHangActivity.this).create();
            ad.setTitle("Thông báo");
            String msg = String.format("Đơn hàng này không tồn tại");
            ad.setMessage(msg);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ad.show();
            return;
        }

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.recGiohang.addItemDecoration(itemDecoration);

        reference.child("donhang").child(user.getUid()).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DonHang donHang1=snapshot.getValue(DonHang.class);
                gioHangList=donHang1.getGioHangList();

                adapter=new GioHangDHAdapter(getApplication(), gioHangList);
                binding.recGiohang.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                binding.recGiohang.setAdapter(adapter);

                if(donHang1.getTrangThai().equals("Chờ xác nhận")){
                    binding.btnHuy.setVisibility(View.VISIBLE);
                    binding.btnMualai.setVisibility(View.GONE);
                    binding.btnDanhGia.setVisibility(View.GONE);
                    binding.btnDaNhan.setVisibility(View.GONE);
                    binding.tvTtdh.setText("Đang chờ người bán xác nhận đơn hàng");
                }else if(donHang1.getTrangThai().equals("Đã nhận")){
                    binding.btnHuy.setVisibility(View.GONE);
                    binding.btnMualai.setVisibility(View.GONE);
                    binding.btnDanhGia.setVisibility(View.VISIBLE);
                    binding.btnDaNhan.setVisibility(View.GONE);
                    binding.tvTtdh.setText("Đơn hàng giao thành công");
                }else if(donHang1.getTrangThai().equals("Đang giao")){
                    binding.btnHuy.setVisibility(View.GONE);
                    binding.btnMualai.setVisibility(View.GONE);
                    binding.btnDanhGia.setVisibility(View.GONE);
                    binding.btnDaNhan.setVisibility(View.VISIBLE);
                    binding.tvTtdh.setText("Đơn hàng đang trong quá trình vận chuyển.");
                }else if(donHang1.getTrangThai().equals("Đã hủy")){
                    binding.btnHuy.setVisibility(View.GONE);
                    binding.btnMualai.setVisibility(View.VISIBLE);
                    binding.btnDanhGia.setVisibility(View.GONE);
                    binding.btnDaNhan.setVisibility(View.GONE);
                    binding.tvTtdh.setText("Đơn hàng đã bị hủy");
                }else{
                    binding.btnHuy.setVisibility(View.GONE);
                    binding.btnMualai.setVisibility(View.VISIBLE);
                    binding.btnDanhGia.setVisibility(View.GONE);
                    binding.btnDaNhan.setVisibility(View.GONE);
                    binding.tvTtdh.setText("Đơn hàng giao thành công");
                }

                thongTinNhanHang=donHang1.getThongTinNhanHang();
                binding.tvUsername.setText(thongTinNhanHang.getUsername());
                binding.tvDc.setText(thongTinNhanHang.getDiachi());
                binding.tvSdt.setText(thongTinNhanHang.getSdt());
                binding.tvMadh.setText(donHang1.getId());
                binding.tvNgay.setText(donHang1.getNgayTao());
                binding.tvThanhtien.setText(donHang1.getTongTien()+"đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.btnDaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("donhang").child(user.getUid()).child(id).child("trangThai").setValue("Đã nhận");
            }
        });
        binding.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("donhang").child(user.getUid()).child(id).child("trangThai").setValue("Đã hủy");
            }
        });

        binding.btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ChiTietDonHangActivity.this, PostDanhGiaActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });

        binding.btnMualai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(GioHang gioHang1: gioHangList){
                    reference.child("giohang").child(user.getUid()).child(gioHang1.getIdsp()).setValue(gioHang1);
                }
                startActivity(new Intent(ChiTietDonHangActivity.this, GioHangActivity.class));
            }
        });

        binding.btnMualai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(GioHang gioHang1: gioHangList){
                    reference.child("giohang").child(user.getUid()).child(gioHang1.getIdsp()).setValue(gioHang1);
                }
                startActivity(new Intent(ChiTietDonHangActivity.this, GioHangActivity.class));
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