package com.example.chargezone1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chargezone1.Adapter.TabLayoutAdapter;
import com.example.chargezone1.R;
import com.google.android.material.tabs.TabLayout;


public class Contact_UsFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact__us, container, false);


        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        TabLayoutAdapter pagerAdapter = new TabLayoutAdapter(requireActivity().getSupportFragmentManager());

        // Add fragments to the PagerAdapter
        pagerAdapter.addFragment(new ContactFragment(), "Contact");
        pagerAdapter.addFragment(new FAQFragment(), "FAQ");


        // Set the adapter for the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
}