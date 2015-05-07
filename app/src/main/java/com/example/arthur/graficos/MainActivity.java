package com.example.arthur.graficos;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    public boolean flag;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    String[] actions = new String[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

        Log.i(null, "--> " + position);

        switch (position) {
            case 0:
                Log.i(null, "ENTROU 0");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commit();
                flag = false;
                break;
            case 1:
                Log.i(null, "ENTROU 1");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FirstFragment.newInstance())
                        .commit();
                actions = new String[] {
                        "Linha Prod 1",
                        "Linha Prod 2",
                        "Linha Prod 3",
                };
                flag = true;
                break;
            case 2:
                Log.i(null, "ENTROU 3");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SecondFragment.newInstance())
                        .commit();
                actions = new String[] {
                        "Linha Prod 1",
                        "Linha Prod 2",
                        "Linha Prod 3",
                        "Linha Prod 4",
                        "Linha Prod 5",
                };
                flag = true;
                break;
        }
    }

    public void ativarMenu(){

        Log.i(null, "ATIVAR SPINNER");

        /** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions);

        /** Enabling dropdown list navigation for the action bar */
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(getBaseContext(), "You selected : " + actions[itemPosition], Toast.LENGTH_SHORT).show();
                return false;
            }
        };

        /** Setting dropdown items and item navigation listener for the actionbar */
        getActionBar().setListNavigationCallbacks(adapter, navigationListener);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setTitle(mTitle);
        if(flag == true) {
            ativarMenu();
        }
        Log.i(null, "RESTORE ACTION");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            Log.i(null, "onCreateOptionsMenu");

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}