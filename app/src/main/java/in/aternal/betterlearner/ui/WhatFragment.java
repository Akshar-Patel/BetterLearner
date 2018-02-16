package in.aternal.betterlearner.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import in.aternal.betterlearner.R;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhatEntry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnWhatFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WhatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhatFragment extends Fragment {

  private static final String ARG_TECHNIQUE_ID = "arg_technique_id";

  private String mTechniqueWhatDesc;
  private String mTechniqueName;

  private OnWhatFragmentInteractionListener mListener;

  public WhatFragment() {
    // Required empty public constructor
  }

  public static WhatFragment newInstance(String techniqueId) {
    WhatFragment fragment = new WhatFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TECHNIQUE_ID, techniqueId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      String techniqueId = getArguments().getString(ARG_TECHNIQUE_ID);
      Cursor cursor;
      if (getActivity() != null) {
        cursor = getActivity().getContentResolver()
            .query(TechniqueWhatEntry.CONTENT_URI_TECHNIQUE_WHAT, null,
                TechniqueWhatEntry.COLUMN_NAME_TECHNIQUE_ID + "=?", new String[]{
                    techniqueId},
                null);

        if (cursor != null) {
          cursor.moveToFirst();
          mTechniqueWhatDesc = cursor
              .getString(cursor.getColumnIndex(TechniqueWhatEntry.COLUMN_NAME_DESC));
          cursor.close();
        }
        Cursor techniqueCursor;
        techniqueCursor = getActivity().getContentResolver()
            .query(TechniqueEntry.CONTENT_URI_TECHNIQUE, null, TechniqueEntry.COLUMN_NAME_ID + "=?",
                new String[]{
                    techniqueId},
                null);
        if (techniqueCursor != null) {
          techniqueCursor.moveToFirst();
          mTechniqueName = techniqueCursor
              .getString(techniqueCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_NAME));
          techniqueCursor.close();
        }
      }
    }
    if (getContext() != null) {
      Bundle bundle = new Bundle();
      bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, WhatFragment.class.getSimpleName());
      MainActivity.sFirebaseAnalytics.logEvent(Event.VIEW_ITEM, bundle);
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    AppCompatActivity appCompatActivity = ((AppCompatActivity) getActivity());
    if (appCompatActivity != null) {
      ActionBar actionBar = appCompatActivity.getSupportActionBar();
      if (actionBar != null) {
        actionBar.setTitle(mTechniqueName);
      }
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_what, container, false);
    TextView techniqueNameTextView = rootView.findViewById(R.id.text_view_heading);
    techniqueNameTextView.setText(
        String.format("What is %s?", mTechniqueName));
    TextView techniqueWhatDescTextView = rootView.findViewById(R.id.text_view_technique_what_desc);
    techniqueWhatDescTextView.setText(mTechniqueWhatDesc);
    return rootView;
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onWhatFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnWhatFragmentInteractionListener) {
      mListener = (OnWhatFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnWhatFragmentInteractionListener");
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
   */
  public interface OnWhatFragmentInteractionListener {

    void onWhatFragmentInteraction(Uri uri);
  }
}
