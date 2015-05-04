package prathameshshetye.mark.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import prathameshshetye.mark.R;
import prathameshshetye.mark.Utilities.Log;
import prathameshshetye.mark.Utilities.Utility;
import prathameshshetye.mark.database.Marker;

public class MapsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final int LOCATION_REFRESH_TIME = 36000;
    private final int LOCATION_REFRESH_DISTANCE = 1000;
    private static final String ARG_MARKERS = "markers";
    private static final String SP_LAT = "sp_lat";
    private static final String SP_LNG = "sp_lng";
    private LatLng mDefLatLng;
    private SharedPreferences mSP;
    private int mDefaultZoom = 1;
    private CameraPosition mCamPos;

    private List<Marker> mMarkers;

    private OnFragmentInteractionListener mListener;

    private MapView mMapView;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private final android.location.LocationListener mLocationListener = new android.location.LocationListener() {
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.LogThis("LocationListner - onStatusChanged s = " + s);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.LogThis("LocationListner - onProviderEnabled s = " + s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.LogThis("LocationListner - onProviderDisabled s = " + s);
        }

        @Override
        public void onLocationChanged(final Location location) {
            if (mMap != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15);
                mMap.animateCamera(cameraUpdate);
                if(mSP == null) {
                    Log.LogThis("mSP IS NULL");
                    return;
                }
                Log.LogThis("Writing Values : " + location.getLatitude() + " & " + location.getLongitude());
                mSP.edit().putFloat(SP_LAT, (float) location.getLatitude()).apply();
                mSP.edit().putFloat(SP_LNG, (float) location.getLongitude()).apply();
            }
        }
    };

    public static MapsFragment newInstance(ArrayList<Marker> markers) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_MARKERS, markers);
        fragment.setArguments(args);
        return fragment;
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMarkers = getArguments().getParcelableArrayList(ARG_MARKERS);
        }
        mSP = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mDefLatLng = new LatLng(mSP.getFloat(SP_LAT, Utility.DEFAULT_LAT),
                mSP.getFloat(SP_LNG, Utility.DEFAULT_LNG));

        if (Utility.DEFAULT_LNG == mDefLatLng.longitude &&
                Utility.DEFAULT_LAT == mDefLatLng.latitude) {
            mDefaultZoom = 1;
        } else {
            mDefaultZoom = 15;
        }
        Log.LogThis("Got the Values : " + mDefLatLng.latitude + " & " + mDefLatLng.longitude);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if(mMapView!=null) {
            try {
                MapsInitializer.initialize(this.getActivity());
                mMap = mMapView.getMap();
                mMap.getUiSettings().setAllGesturesEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(false);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefLatLng, mDefaultZoom));
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.setBuildingsEnabled(true);
                mMap.setIndoorEnabled(true);
                mMap.setTrafficEnabled(true);
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Marked"));
                        if (mListener != null) {
                            mListener.startAddMarkerFragment((float)latLng.latitude, (float)latLng.longitude);
                        }
                    }
                });

                mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                        LOCATION_REFRESH_DISTANCE, mLocationListener);
            } catch (InflateException e) {
                Toast.makeText(getActivity(), "Problems inflating the view !",
                        Toast.LENGTH_LONG).show();
                android.util.Log.e("ERROR", "ERROR", e);
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), "Google Play Services missing !",
                        Toast.LENGTH_LONG).show();
                android.util.Log.e("ERROR","ERROR",e);
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMap != null) {
            mCamPos = mMap.getCameraPosition();
        }
    }

    @Override
    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.LogThis("in OnDestroy - MapsFragment");
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void startAddMarkerFragment(float lat, float lng);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.LogThis("Connection Successfull");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.LogThis("Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.LogThis("Connection Failed");
    }
}
