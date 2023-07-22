package com.example.ebook01.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebook01.LoginActivity;
import com.example.ebook01.MainActivity;
import com.example.ebook01.dao.UserDao;
import com.example.ebook01.databinding.FragmentPersonBinding;
import com.example.ebook01.utils.ShareHelper;

public class PersonFragment extends Fragment {

    private FragmentPersonBinding binding;
    private TextView username,userpoint;

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }
    UserDao userDao;
    ShareHelper shareHelper;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =FragmentPersonBinding.inflate(inflater,container,false);
        username =binding.username;
        userpoint =binding.userpoint;
        View root = binding.getRoot();
        userDao = new UserDao(getActivity());
        shareHelper = new ShareHelper(getActivity());
        String defout = "Failed to get";
        String name = (String)shareHelper.get("username", defout);
        int point = (int)shareHelper.get("point",0);

        username.setText(name);
        userpoint.setText(String.valueOf(point));

        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                Intent intent = new Intent();
                intent.setClass(mainActivity,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}