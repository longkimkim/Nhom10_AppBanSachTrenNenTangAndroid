package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.fragment.TaiKhoanFragment;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityChangePassBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangePassActivity extends AppCompatActivity {
    ActivityChangePassBinding binding;
    ProgressDialog progressDialog;
    private ArrayList<TaiKhoan> listTaiKhoan = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(ChangePassActivity.this, R.layout.activity_change_pass);
        progressDialog = new ProgressDialog(this);
        setUserInformation();
        binding.toolbarCapnhatmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
                intent.putExtra("trang", 3);
                startActivity(intent);
            }
        });
        binding.btnCapNhatChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePass();
            }
        });
    }
    public  void updatePass(){
        String mkcu=binding.tvMkcuChangepass.getText().toString().trim();
        String mkmoi=binding.tvMkmoiChangepass.getText().toString().trim();
        String nhaclaimk=binding.tvNhaclaimkChangepass.getText().toString().trim();
        if (mkcu.equals("") || mkmoi.equals("")|| nhaclaimk.equals("")){
            AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
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
            if(mkmoi.equals(mkcu)){
                progressDialog.show();
                progressDialog.dismiss();
                AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
                ad.setTitle("Thông báo");
                String msg = String.format("Mật khẩu mới không được trùng với mật khẩu cũ!");
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
                if(mkmoi.equals(nhaclaimk)){
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),mkcu);
                    progressDialog.show();
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                user.updatePassword(mkmoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
                                            ad.setTitle("Thông báo");
                                            String msg = String.format("Cập nhật thành công!");
                                            ad.setMessage(msg);
                                            ad.setIcon(android.R.drawable.ic_dialog_info);
                                            ad.setButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                }
                                            });
                                            ad.show();
                                        }else {
                                            AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
                                            ad.setTitle("Thông báo");
                                            String msg = String.format("Cập nhật thất bại!");
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
                            }else {
                                AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
                                ad.setTitle("Thông báo");
                                String msg = String.format("Mật khẩu cũ không đúng!");
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
                else{
                    AlertDialog ad = new AlertDialog.Builder(ChangePassActivity.this).create();
                    ad.setTitle("Thông báo");
                    String msg = String.format("Nhắc lại mật khẩu sai!");
                    ad.setMessage(msg);
                    ad.setIcon(android.R.drawable.ic_dialog_info);
                    ad.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    ad.show();
                }
                setText();
            }

        }

    }
 public void setText(){
        binding.tvMkcuChangepass.setText("");
        binding.tvMkmoiChangepass.setText("");
        binding.tvNhaclaimkChangepass.setText("");
 }
    public   void setUserInformation(){
        if(user==null){
            return;
        }
        else{
            String email = user.getEmail();
            binding.tvEmailChangepass.setText(email);
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference("taikhoan");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                        if(taiKhoan.getId_User().equals(user.getUid())){
                            listTaiKhoan.add(taiKhoan);
                        }
                    }
                    binding.tvTenDNChangepass.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getUsername());
                    Glide.with(ChangePassActivity.this).load(listTaiKhoan.get(listTaiKhoan.size()-1).getAvt()).error(R.drawable.avatardefault).into(binding.imgUpdateAvatarChangepass);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
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