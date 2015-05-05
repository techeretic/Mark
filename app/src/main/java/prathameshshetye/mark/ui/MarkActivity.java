package prathameshshetye.mark.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prathameshshetye.mark.R;
import prathameshshetye.mark.Utilities.Log;

public class MarkActivity extends AppCompatActivity implements
                            MapsFragment.OnFragmentInteractionListener,
                            Markers.OnFragmentInteractionListener,
                            AddMarker.OnFragmentInteractionListener {
    private boolean mIsMap = true;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private DrawerLayout mDrawer;

    private ActionBarDrawerToggle mDrawerToggle;

    List<DrawerItems> drawerItems = new ArrayList<DrawerItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        drawerItems.add(new DrawerItems(getString(R.string.navi_drawer_map), R.drawable.ic_maps));
        drawerItems.add(new DrawerItems(getString(R.string.navi_drawer_marks), R.drawable.ic_bookmark));

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecAdapter(drawerItems, getString(R.string.title_activity_mark));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDrawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawer.openDrawer(mRecyclerView);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawer,
                null, //ToolBar
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Something when drawer opens
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };

        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.LogThis("Position clicked on : " + position);
                TextView tv = (TextView) view.findViewById(R.id.rowText);
                if (tv != null) {
                    if (tv.getText().toString().equals(getString(R.string.navi_drawer_map)))
                        mIsMap = true;
                    else
                        mIsMap = false;
                    Log.LogThis("Clicked on : " + tv.getText().toString());
                    loadFragment();
                    mDrawer.closeDrawers();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        loadFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mark, menu);
        return true;
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

    private void loadFragment() {
        Log.LogThis("In LoadFragment " + getSupportFragmentManager().getBackStackEntryCount());
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = mIsMap ? new MapsFragment() : new Markers();
        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .addToBackStack(mIsMap ? "MapFragment" : "Markers")
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //Do Something
    }

    @Override
    public void openDrawer() {
        if (mDrawer != null) {
            mDrawer.openDrawer(mRecyclerView);
        }
    }

    @Override
    public void startAddMarkerFragment(float lat, float lng) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, AddMarker.newInstance(lat, lng))
                .addToBackStack("AddMarker")
                .commit();
    }

    @Override
    public void endFragment(Fragment frag) {
        Log.LogThis("in endFragment in Activity");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void checkIfNeedToExit() {
        if (getSupportFragmentManager().getBackStackEntryCount()==0) {
            finish();
        }
    }
}
