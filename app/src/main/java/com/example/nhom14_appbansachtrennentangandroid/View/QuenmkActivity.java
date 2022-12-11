package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncListUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityChangePassBinding;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityQuenmkBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class QuenmkActivity extends AppCompatActivity {

    ActivityQuenmkBinding binding;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog;
    String email;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(QuenmkActivity.this, R.layout.activity_quenmk);
        progressDialog = new ProgressDialog(this);
        binding.toolbarQuenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuenmkActivity.this, DangNhap.class);
                startActivity(intent);
            }
        });
        binding.btnKhoiphuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               send();
            }
        });

    }

    public  void send() {
        email=binding.edEmailQuenmk.getText().toString().trim();
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (email.equals("")) {
            AlertDialog ad = new AlertDialog.Builder(QuenmkActivity.this).create();
            ad.setTitle("Thông báo");
            String msg = String.format("Vui lòng nhập đầy đủ thông tin!");
            ad.setMessage(msg);
            ad.setIcon(android.R.drawable.ic_dialog_info);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            ad.show();
        } else {
            progressDialog.show();
            mFirebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( @NotNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                AlertDialog ad = new AlertDialog.Builder(QuenmkActivity.this).create();
                                ad.setTitle("Thông báo");
                                String msg = String.format("Vui lòng kiểm tra email để lấy lại mật khẩu!");
                                ad.setMessage(msg);
                                ad.setIcon(android.R.drawable.ic_dialog_info);
                                ad.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                    }
                                });
                                ad.show();
                            }
                            else {
                                AlertDialog ad = new AlertDialog.Builder(QuenmkActivity.this).create();
                                ad.setTitle("Thông báo");
                                String msg = String.format("Gửi email thất bại!");
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