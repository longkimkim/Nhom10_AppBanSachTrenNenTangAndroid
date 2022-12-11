package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.fragment.HomeFragment;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DanhGiaAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityChiTietSpactivityBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhGia;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSPActivity extends AppCompatActivity {

    ActivityChiTietSpactivityBinding binding;
    int sl = 0;
    String maSP = "";
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    public static TextView tvSoLuongGioHang;
    DanhGiaAdapter danhGiaAdapter;
    List<DanhGia> danhGiaList= new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<SanPham> sanPhamList=new ArrayList<>();
    List<GioHang> gioHangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ChiTietSPActivity.this, R.layout.activity_chi_tiet_spactivity);
        tvSoLuongGioHang = findViewById(R.id.tvSoLuongGioHang_CTSP);
        Intent intent = getIntent();
        maSP = intent.getStringExtra("maSP");

        //load dl
        display();
        displayDanhGia();
        getSoLuongGiohang();

        if(maSP==null){
            AlertDialog ad = new AlertDialog.Builder(ChiTietSPActivity.this).create();
            ad.setTitle("Thông báo");
            String msg = String.format("Sản phẩm này không tồn tại!");
            ad.setMessage(msg);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ad.show();

            return;
        }


        setSupportActionBar(binding.toolbarSp);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");


        danhGiaAdapter = new DanhGiaAdapter(danhGiaList, getApplicationContext());
        binding.recDanhGia.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recDanhGia.setAdapter(danhGiaAdapter);



        binding.btnXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChiTietSPActivity.this, DanhGiaActivity.class);
                intent1.putExtra("maSP", maSP);
                startActivity(intent1);
                finish();
            }
        });


        binding.tvXemAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChiTietSPActivity.this, DanhGiaActivity.class);
                intent1.putExtra("maSP", maSP);
                startActivity(intent1);
                finish();
            }
        });


        sl = Integer.parseInt(binding.edSl.getText().toString());
        binding.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl += 1;
                binding.edSl.setText(sl + "");
            }
        });


        binding.btnThem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    Toast.makeText(getApplication(),"Đăng nhập để thêm giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sl > 0) {
                    boolean exists = false;

                    if(sanPhamList.size()>0){
                        SanPham sanPham=sanPhamList.get(0);
                        if (gioHangList.size() > 0) {
                            GioHang gioHang1 = new GioHang(maSP,sanPham.getTenSP(), sanPham.getImg(), sanPham.getDonGia(), gioHangList.get(0).getSoluong()+sl );
                            for(int i=0;i<MainActivity.listGioHang.size();i++){
                                if(MainActivity.listGioHang.get(i).getIdsp().equals(gioHang1.getIdsp())){
                                    MainActivity.listGioHang.remove(i);
                                    MainActivity.listGioHang.add(gioHang1);
                                    exists = true;
                                }
                            }
                            if(exists == false){
                                MainActivity.listGioHang.add(gioHang1);
                            }
                            reference.child("giohang").child(user.getUid()).child(gioHang1.getIdsp()).setValue(gioHang1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });

                        } else {
                            GioHang gioHang = new GioHang(maSP,sanPham.getTenSP(), sanPham.getImg(), sanPham.getDonGia(), sl);
                            MainActivity.listGioHang.add(gioHang);
                            reference.child("giohang").child(user.getUid()).child(gioHang.getIdsp()).setValue(gioHang);
                        }
                        AlertDialog ad = new AlertDialog.Builder(ChiTietSPActivity.this).create();
                        ad.setTitle("Thông báo");
                        String msg = String.format("Thêm giỏ hàng thành công!");
                        ad.setMessage(msg);
                        ad.setIcon(android.R.drawable.ic_dialog_info);
                        ad.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        ad.show();

                    }
                    getSoLuongGiohang();
                    HomeFragment.getSoLuongGiohang();
                } else {
                    AlertDialog ad = new AlertDialog.Builder(ChiTietSPActivity.this).create();
                    ad.setTitle("Thông báo");
                    String msg = String.format("Số lượng phải lớn hơn 0!");
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


        binding.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl -= 1;
                if (sl < 0) {
                    sl = 0;
                }
                binding.edSl.setText(sl + "");
            }
        });


        binding.imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSPActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });


        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSPActivity.this, MainActivity.class);
                intent.putExtra("trang", 1);
                startActivity(intent);
            }
        });

        binding.imgQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        binding.tvXemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                binding.tvMota.setLayoutParams(lp);
                binding.tvAnbot.setVisibility(View.VISIBLE);
                binding.tvXemthem.setVisibility(View.GONE);
            }
        });


        binding.tvAnbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                binding.tvMota.setLayoutParams(lp);
                binding.tvAnbot.setVisibility(View.GONE);
                binding.tvXemthem.setVisibility(View.VISIBLE);
            }
        });



        binding.imgQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }//het onCreate







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sp_chi_tiet, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mn_ve_trang_chu:
                startActivity(new Intent(ChiTietSPActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void display() {
        reference.child("sanpham").child(maSP).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SanPham sanPham = snapshot.getValue(SanPham.class);
                binding.tvGiaGoc.setText(sanPham.getDonGia() + "đ");
                binding.tvMota.setText(sanPham.getMoTa());
                binding.tvTenSP.setText(sanPham.getTenSP());
                binding.tvTacgia.setText(sanPham.getTenTacGia());
                binding.tvNxb.setText(sanPham.getNxb());
                Glide.with(getApplicationContext()).load(sanPham.getImg()).error(R.drawable.anhnen).into(binding.imgAnhHang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        reference.child("giohang").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gioHangList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GioHang gioHang = dataSnapshot.getValue(GioHang.class);
                    if (gioHang.getIdsp().equals(maSP)) {
                        gioHangList.add(gioHang);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("sanpham").child(maSP).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhamList.clear();
                SanPham sanPham=snapshot.getValue(SanPham.class);
                sanPhamList.add(sanPham);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayDanhGia() {
        reference.child("danhgia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhGiaList.clear();
                int i = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhGia danhGia = dataSnapshot.getValue(DanhGia.class);
                    if (danhGia.getIdSp().equals(maSP)) {
                        danhGiaList.add(danhGia);
                        i++;
                    }
                    if (i == 3) {
                        break;
                    }
                }
                danhGiaAdapter.notifyDataSetChanged();
                if(danhGiaList.size()<=0){
                    binding.tvChuaco.setVisibility(View.VISIBLE);
                    binding.lnDanhgia.setVisibility(View.GONE);
                }else{
                    binding.tvChuaco.setVisibility(View.GONE);
                    binding.lnDanhgia.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   public static void getSoLuongGiohang(){
        int SoLuong = 0;
        for(int i = 0; i< MainActivity.listGioHang.size(); i++){
            SoLuong += MainActivity.listGioHang.get(i).getSoluong();
        }
        tvSoLuongGioHang.setText(SoLuong+"");
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