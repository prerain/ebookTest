package com.example.ebook01.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class MarkNoteAdapter extends FragmentStateAdapter {
    List<Fragment> fragments;

    public MarkNoteAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
