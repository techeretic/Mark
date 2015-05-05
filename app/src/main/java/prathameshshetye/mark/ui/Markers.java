package prathameshshetye.mark.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import prathameshshetye.mark.R;
import prathameshshetye.mark.Utilities.Log;
import prathameshshetye.mark.database.DatabaseHelper;
import prathameshshetye.mark.database.Marker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Markers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Markers extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Toolbar mToolbar;
    private RecyclerView mRecView;
    private MarkersAdapter mMarkersAdapter;
    private List<Marker> mMarkers;

    public Markers() {
        Log.LogThis("Creating Markers");
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_markers, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.title_activity_mark);
        mToolbar.setNavigationIcon(R.drawable.ic_mark_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.openDrawer();
                }
            }
        });
        mMarkers = DatabaseHelper.getInstance(getActivity()).getAllMarkers();
        mRecView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mMarkersAdapter = new MarkersAdapter(mMarkers);
        mRecView.setAdapter(mMarkersAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.openDrawer();
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
        public void openDrawer();
    }

}
