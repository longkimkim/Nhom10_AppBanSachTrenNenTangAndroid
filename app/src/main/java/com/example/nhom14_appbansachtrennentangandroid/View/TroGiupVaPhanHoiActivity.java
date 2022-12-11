package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityTroGiupVaPhanHoiBinding;

public class TroGiupVaPhanHoiActivity extends AppCompatActivity {
    ActivityTroGiupVaPhanHoiBinding binding;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(TroGiupVaPhanHoiActivity.this, R.layout.activity_tro_giup_va_phan_hoi);
        binding.question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer1.setVisibility(View.VISIBLE);
            }
        });
        binding.question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer2.setVisibility(View.VISIBLE);
            }
        });
        binding.question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer3.setVisibility(View.VISIBLE);
            }
        });
        binding.question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer4.setVisibility(View.VISIBLE);
            }
        });
        binding.question5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer5.setVisibility(View.VISIBLE);
            }
        });
        binding.question6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer6.setVisibility(View.VISIBLE);
            }
        });
        binding.question7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer7.setVisibility(View.VISIBLE);
            }
        });
        binding.question8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer8.setVisibility(View.VISIBLE);
            }
        });
        binding.question9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer9.setVisibility(View.VISIBLE);
            }
        });
        binding.question10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                binding.answer10.setVisibility(View.VISIBLE);
            }
        });
        binding.backTGVPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private  void Gone(){
        binding.answer1.setVisibility(View.GONE);
        binding.answer2.setVisibility(View.GONE);
        binding.answer3.setVisibility(View.GONE);
        binding.answer4.setVisibility(View.GONE);
        binding.answer5.setVisibility(View.GONE);
        binding.answer6.setVisibility(View.GONE);
        binding.answer7.setVisibility(View.GONE);
        binding.answer8.setVisibility(View.GONE);
        binding.answer9.setVisibility(View.GONE);
        binding.answer10.setVisibility(View.GONE);
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