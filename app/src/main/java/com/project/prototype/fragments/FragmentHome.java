package com.project.prototype.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.prototype.MainActivity;
import com.project.prototype.R;


public class FragmentHome extends Fragment {

        public static FragmentHome newInstance() {
            FragmentHome fragment = new FragmentHome();

            return fragment;
        }

        public FragmentHome() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(1);
        }
}
