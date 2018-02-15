package in.aternal.betterlearner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

  private String mTechniqueId;
  private String mTechniqueWhatDesc;

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
      mTechniqueId = getArguments().getString(ARG_TECHNIQUE_ID);
      Cursor cursor;
      if (getActivity() != null) {
        cursor = getActivity().getContentResolver()
            .query(TechniqueWhatEntry.CONTENT_URI_TECHNIQUE_WHAT, null, TechniqueWhatEntry.COLUMN_NAME_TECHNIQUE_ID + "=?", new String[]{
                    mTechniqueId},
                null);
        if(cursor != null){
          cursor.moveToFirst();
          mTechniqueWhatDesc = cursor.getString(cursor.getColumnIndex(TechniqueWhatEntry.COLUMN_NAME_DESC));
          cursor.close();
        }
      }

    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_what, container, false);
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
