package com.example.appmobile.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appmobile.fragment.DaGiaoFragment;
import com.example.appmobile.fragment.DangGiaoFragment;
import com.example.appmobile.fragment.XacNhanFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numPage;


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numPage = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new XacNhanFragment();
            case 1: return new DangGiaoFragment();
            case 2: return new DaGiaoFragment();
        }
        return new XacNhanFragment();
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Chờ xác nhận";
            case 1: return "Đang giao";
            case 2: return "Đã giao";
        }
        return "Chờ xác nhận";
    }
}
