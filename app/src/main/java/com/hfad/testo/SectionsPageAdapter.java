package com.hfad.testo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPageAdapter extends FragmentPagerAdapter {
    private Context context;
    public SectionsPageAdapter(@NonNull FragmentManager fm,Context ct) {
        super(fm);
        context=ct;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                return new TopFragment();
            case 1:
                return new PizzaFragment();
            case 2:
                return new PastaFragment();
            case 3:
                return new StoresFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.home_tab);
            case 1:
                return context.getResources().getString(R.string.pizza_tab);
            case 2:
                return context.getResources().getString(R.string.pasta_tab);
            case 3:
                return context.getResources().getString(R.string.store_tab);
        }
        return null;
    }
}
