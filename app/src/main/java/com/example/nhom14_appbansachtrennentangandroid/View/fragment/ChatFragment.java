package com.example.nhom14_appbansachtrennentangandroid.View.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom14_appbansachtrennentangandroid.R;
import com.example.nhom14_appbansachtrennentangandroid.adapter.ChatAdapter;
import com.example.nhom14_appbansachtrennentangandroid.adapter.DanhGiaAdapter;
import com.example.nhom14_appbansachtrennentangandroid.model.Chat;
import com.example.nhom14_appbansachtrennentangandroid.model.DanhGia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatFragment extends Fragment {

    EditText etNoiDung;
    Button btnSend;
    private RecyclerView rcvChat;
    ChatAdapter chatAdapter;
    List<Chat> chatList;
    String id_chat = "";
    View view;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        etNoiDung = view.findViewById(R.id.et_noidung);
        btnSend = view.findViewById(R.id.btn_send);
        rcvChat = view.findViewById(R.id.rcv_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvChat.setLayoutManager(linearLayoutManager);

        chatList = new ArrayList<>();
        displayChat();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        etNoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyboard();
            }
        });
        return view;
    }
    private void sendMessage(){
        String strNoiDung = etNoiDung.getText().toString().trim();
        DateFormat df = new SimpleDateFormat("dd-MM-yyy 'at' HH:mm");
        String thoiGian = df.format(Calendar.getInstance().getTime());

        if(TextUtils.isEmpty(strNoiDung)){
            return;
        }
        id_chat = reference.child("chat").push().getKey();
        Chat chat = new Chat(user.getUid(), id_chat, strNoiDung, thoiGian);
        chatList.add(chat);
        reference.child("chat").child(chat.getId_chat()).setValue(chat);
        chatAdapter.notifyDataSetChanged();
        rcvChat.scrollToPosition(chatList.size()-1);

        etNoiDung.setText("");

    }
    private void displayChat(){
        reference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat= dataSnapshot.getValue(Chat.class);
                    if(chat.getId_User().equals(user.getUid())) {
                        chatList.add(chat);
                    }
                }
                chatAdapter = new ChatAdapter();
                chatAdapter.setData(chatList, getContext());
                rcvChat.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Get Message Fail!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkKeyboard(){
        final View activityRootView = view.findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25*activityRootView.getRootView().getHeight()){
                    if(chatList.size() > 0){
                        rcvChat.scrollToPosition(chatList.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}