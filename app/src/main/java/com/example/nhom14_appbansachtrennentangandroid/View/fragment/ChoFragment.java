package com.example.nhom14_appbansachtrennentangandroid.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DSDonHangAdapter;
import com.example.nhom14_appbansachtrennentangandroid.model.DonHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChoFragment extends Fragment {
    RecyclerView rec_donhang;
    RelativeLayout ln_chuaDH;
    List<DonHang> donHangList;
    DSDonHangAdapter adapter;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cho, container, false);
        rec_donhang=view.findViewById(R.id.rec_donhang);
        ln_chuaDH=view.findViewById(R.id.ln_chuaDH);
        donHangList=new ArrayList<>();

        rec_donhang.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new DSDonHangAdapter(donHangList, getContext());
        rec_donhang.setAdapter(adapter);
        layDonHang();

        return view;
    }

    private void layDonHang(){
        reference.child("donhang").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DonHang donHang=dataSnapshot.getValue(DonHang.class);
                    if(donHang.getTrangThai().equals("Chờ xác nhận")){
                        donHangList.add(donHang);
                    }
                }
                if(donHangList.size()>0){
                    adapter.notifyDataSetChanged();
                    rec_donhang.setVisibility(View.VISIBLE);
                    ln_chuaDH.setVisibility(View.GONE);
                }else{
                    rec_donhang.setVisibility(View.GONE);
                    ln_chuaDH.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}