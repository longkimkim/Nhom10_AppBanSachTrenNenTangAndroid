package com.example.nhom14_appbansachtrennentangandroid.View;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DonHangViewPager;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityDonHangBinding;
import com.google.android.material.navigation.NavigationBarView;

public class DonHangActivity extends AppCompatActivity {

    ActivityDonHangBinding binding;
    DonHangViewPager viewPager;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(DonHangActivity.this, R.layout.activity_don_hang);


        setSupportActionBar(binding.toolbarSp);
        getSupportActionBar().setTitle("Đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager =new DonHangViewPager(this);
        binding.viewpagerMain.setAdapter(viewPager);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.nav_cho){
                    binding.viewpagerMain.setCurrentItem(0);
                }
                else if(id==R.id.nav_dang){
                    binding.viewpagerMain.setCurrentItem(1);
                }
                else if(id==R.id.nav_da){
                    binding.viewpagerMain.setCurrentItem(2);
                }
                else if(id==R.id.nav_huy){
                    binding.viewpagerMain.setCurrentItem(3);
                }
                return true;
            }
        });


        
        binding.viewpagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_cho).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_dang).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_da).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_huy).setChecked(true);
                        break;
                }
            }
        });



        binding.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonHangActivity.this, MainActivity.class);
                intent.putExtra("trang", 1);
                startActivity(intent);
                finish();
            }
        });


        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonHangActivity.this, TimKiemDonActivity.class);
                startActivity(intent);
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