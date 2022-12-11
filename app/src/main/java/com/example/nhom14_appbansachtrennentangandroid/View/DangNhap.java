package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityDangNhapBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    ActivityDangNhapBinding binding;
    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(DangNhap.this,R.layout.activity_dang_nhap );
        binding.tvQuenMKDN .setPaintFlags(binding.tvQuenMKDN.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        binding.tvDkiDN.setPaintFlags(binding.tvDkiDN.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        progressDialog = new ProgressDialog(this);
        binding.tvDkiDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyensangdangky();
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
        binding.tvQuenMKDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyensangquenmk();
            }
        });
    }
    private  void  chuyensangquenmk(){
        Intent intent = new Intent(DangNhap.this,QuenmkActivity.class);
        startActivity(intent);
    }
    private  void  chuyensangdangky(){
        Intent intent = new Intent(DangNhap.this,DangKyActivity.class);
        startActivity(intent);
    }
    private  void dangnhap(){
        String email = binding.edEmailLogin.getText().toString().trim();
        String password = binding.edPassLogin.getText().toString().trim();
        if (email.equals("") || password.equals("")){
            AlertDialog ad = new AlertDialog.Builder(DangNhap.this).create();
            ad.setTitle("Thông báo");
            String msg = String.format("Vui lòng nhập đầy đủ thông tin!");
            ad.setMessage(msg);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            ad.show();
        }
        else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog ad = new AlertDialog.Builder(DangNhap.this).create();
                                ad.setTitle("Thông báo");
                                String msg = String.format("Đăng nhập thất bại !");
                                ad.setMessage(msg);
                                ad.setIcon(android.R.drawable.ic_dialog_info);
                                ad.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                    }
                                });
                                ad.show();
                            }
                        }
                    });
        }
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