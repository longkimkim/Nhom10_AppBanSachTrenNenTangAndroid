package com.example.nhom14_appbansachtrennentangandroid.View.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.CapNhatTKActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.ChangePassActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.DangNhap;
import com.example.nhom14_appbansachtrennentangandroid.View.DonHangActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.MainActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.ThongTinShopActivity;
import com.example.nhom14_appbansachtrennentangandroid.View.TroGiupVaPhanHoiActivity;
import com.example.nhom14_appbansachtrennentangandroid.model.SanPham;
import com.example.nhom14_appbansachtrennentangandroid.model.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
;import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanFragment extends Fragment {
    View mView;
    LinearLayout tv_capnhattk,tv_thongtindonhang,tv_thongtinshop,tv_trogiup,tv_dangxuat,tv_thaydoimk;
    private ImageView imgAvatar;
    private TextView tv_display_name,tv_display_email;
    private ArrayList<TaiKhoan> listTaiKhoan= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        anhxa();
        showInformation();
        tv_capnhattk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CapNhatTKActivity.class);
                startActivity(intent);
            }
        });
        tv_thaydoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassActivity.class);
                startActivity(intent);
            }
        });
        tv_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), DangNhap.class));
//                FirebaseAuth.getInstance().signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), DangNhap.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        tv_thongtindonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DonHangActivity.class));
            }
        });
        tv_thongtinshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThongTinShopActivity.class));
            }
        });
        tv_trogiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TroGiupVaPhanHoiActivity.class));
            }
        });
        return  mView;

    }
    private void anhxa(){
        tv_capnhattk=mView.findViewById(R.id.tv_capnhattaikhoan);
        tv_thaydoimk=mView.findViewById(R.id.tv_thaydoimk);
        tv_thongtindonhang=mView.findViewById(R.id.tv_thongtindonhang);
        tv_thongtinshop=mView.findViewById(R.id.tv_thongtinshop);
        tv_trogiup=mView.findViewById(R.id.tv_trogiup);
        tv_dangxuat=mView.findViewById(R.id.tv_dangxuat);
        imgAvatar= mView.findViewById(R.id.img_avatar_display);
        tv_display_name=mView.findViewById(R.id.tv_display_name);
        tv_display_email=mView.findViewById(R.id.tv_display_email);
    }

    public   void showInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        else{
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
                    if (user.getDisplayName()==null){
                        tv_display_name.setVisibility(View.GONE);
                    }
                    else {
                        tv_display_name.setVisibility(View.VISIBLE);
                        tv_display_name.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getUsername());
                    }
                    tv_display_email.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getEmail());
                    Glide.with(getContext()).load(listTaiKhoan.get(listTaiKhoan.size()-1).getAvt()).error(R.drawable.avatardefault).into(imgAvatar);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(),"Get Book Fail!",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


}
