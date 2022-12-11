package com.example.nhom14_appbansachtrennentangandroid.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.View.fragment.TaiKhoanFragment;
import com.example.nhom14_appbansachtrennentangandroid.adapter.NetworkChangeListener;
import com.example.nhom14_appbansachtrennentangandroid.databinding.ActivityCapNhatTkactivityBinding;
import com.example.nhom14_appbansachtrennentangandroid.model.GioHang;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CapNhatTKActivity extends AppCompatActivity {
    ActivityCapNhatTkactivityBinding binding;
    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private  Uri mUri ;
    DateFormat fmtDateAndTime = DateFormat.getDateInstance();
    Calendar myCalendar = Calendar.getInstance();
    public static  final int MY_REQUEST_CODE=10;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private ArrayList<TaiKhoan> listTaiKhoan = new ArrayList<>();
    private LocationManager locationManager;
    private int PEMISSION_CODE =1;



    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEditText();
        }
    };
    private void updateEditText() {
        binding.tvDisplayNgaysinhCapnhat.setText(myCalendar.get(Calendar.DAY_OF_MONTH)+"/"+(myCalendar.get(Calendar.MONTH)+1)+"/"+myCalendar.get(Calendar.YEAR));
    }

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();
                if(intent==null){
                    return;
                }
                mUri = intent.getData();
                setmUri(mUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mUri);
                    setBitMapImageView(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(CapNhatTKActivity.this, R.layout.activity_cap_nhat_tkactivity);
        progressDialog = new ProgressDialog(CapNhatTKActivity.this);
        binding.tvDisplayNgaysinhCapnhat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CapNhatTKActivity.this, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        updateEditText();
        setUserInformation();
        initListener();
        binding.toolbarCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatTKActivity.this, MainActivity.class);
                intent.putExtra("trang", 3);
                startActivity(intent);
            }
        });
        binding.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatTKActivity.this, DiaChiActivity.class);
                startActivity(intent);
            }
        });

    }
    private void initListener() {
        binding.imgUpdateAvatarCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        binding.btnCapNhatCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });
    }
    private  void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        if(user==null){
            return;
        }
        String tenDN= binding.tvDisplayTenDNCapnhat.getText().toString().trim();
        String sdt = binding.tvDisplaySdtCapnhat.getText().toString().trim();
        String diachi = binding.tvDisplayDiachiCapnhat.getText().toString().trim();
        String ngaysinh = binding.tvDisplayNgaysinhCapnhat.getText().toString().trim();
        Boolean check;
        if(binding.rdbNam.isChecked()) check=true;
        else check=false;

        progressDialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(tenDN)
                .setPhotoUri(mUri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            AlertDialog ad = new AlertDialog.Builder(CapNhatTKActivity.this).create();
                            ad.setTitle("Thông báo");
                            String msg = String.format("Cập nhật thành công !");
                            ad.setMessage(msg);
                            ad.setIcon(android.R.drawable.ic_dialog_info);
                            ad.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    String gt="";
                                    if(check==true) gt="Nam";
                                    else gt="Nữ";
                                    TaiKhoan t = new TaiKhoan(user.getPhotoUrl()+"",diachi,user.getEmail()+"",gt,user.getUid()+"",ngaysinh,sdt,tenDN);
                                    databaseReference.child("taikhoan").child(user.getUid()+"").setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                            }
                                        }
                                    });
                                }
                            });
                            ad.show();
                            setUserInformation();
//                            taiKhoanFragment.showInformation();
                        }
                    }
                });
    }
    private  void onClickRequestPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallary();
            return;
        }
        if(getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
           openGallary();
        }
        else{
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }
    public   void setUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        else{
            String name = user.getDisplayName();
            String email = user.getEmail();
            if (name==null){
                binding.tvDisplayTenDNCapnhat.setVisibility(View.GONE);
            }
            else {
                binding.tvDisplayTenDNCapnhat.setVisibility(View.VISIBLE);
                binding.tvDisplayTenDNCapnhat.setText(name);
            }
            binding.tvDisplayEmailCapnhat.setText(email);
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
                    binding.tvDisplaySdtCapnhat.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getSdt());
                    binding.tvDisplayDiachiCapnhat.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getDiachi());
                    binding.tvDisplayNgaysinhCapnhat.setText(listTaiKhoan.get(listTaiKhoan.size()-1).getNgaysinh());
                    if(listTaiKhoan.get(listTaiKhoan.size()-1).getGioitinh().equals("Nam")){
                        binding.rdbNam.setChecked(true);
                    }
                    else if(listTaiKhoan.get(listTaiKhoan.size()-1).getGioitinh().equals("Nữ")){
                        binding.rdbNu.setChecked(true);
                    }
                    Glide.with(CapNhatTKActivity.this).load(listTaiKhoan.get(listTaiKhoan.size()-1).getAvt()).error(R.drawable.avatardefault).into(binding.imgUpdateAvatarCapnhat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
    }
    public  void setBitMapImageView(Bitmap bitMapImageView){
        binding.imgUpdateAvatarCapnhat.setImageBitmap(bitMapImageView);
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallary();
            }
        }
    }
    public  void openGallary(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));
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