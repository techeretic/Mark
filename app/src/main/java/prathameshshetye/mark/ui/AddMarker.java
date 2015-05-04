package prathameshshetye.mark.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import prathameshshetye.mark.R;
import prathameshshetye.mark.Utilities.Log;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMarker.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddMarker extends Fragment {

    private static final String ARG_LAT="lat";
    private static final String ARG_LNG="lng";

    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;
    private float mLat;
    private float mLng;

    public AddMarker() {
        // Required empty public constructor
    }

    public static AddMarker newInstance(float lat, float lng) {
        AddMarker fragment = new AddMarker();
        Bundle args = new Bundle();
        args.putFloat(ARG_LAT, lat);
        args.putFloat(ARG_LNG, lng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLat = getArguments().getFloat(ARG_LAT);
            mLng = getArguments().getFloat(ARG_LNG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_marker, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.monitor_toolbar);
        mToolbar.setTitle(R.string.marker_add);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endFragment();
            }
        });
        TextView lat = (TextView) view.findViewById(R.id.marker_latitude);
        TextView lng = (TextView) view.findViewById(R.id.marker_longitude);

        lat.setText(Float.toString(mLat));
        lng.setText(Float.toString(mLng));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
        void endFragment(Fragment frag);
    }

    public void endFragment() {
        if (mListener != null) {
            Log.LogThis("Calling endFragment on Listener");
            mListener.endFragment(this);
        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        endFragment();
                        return true;
                    }
                    return false;
                }
            });
        }
    }*/
}
