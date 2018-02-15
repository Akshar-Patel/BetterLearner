package in.aternal.betterlearner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueHowEntry;
import in.aternal.betterlearner.model.TechniqueHow.Step;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHowFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HowFragment extends Fragment {

  private static final String ARG_TECHNIQUE_ID = "arg_technique_id";

  private String mTechniqueId;
  private RecyclerView mTechniqueHowStepRecyclerView;
  private TechniquesHowStepRecyclerViewAdapter mTechniquesHowBenefitRecyclerViewAdapter;
  private static List<Step> mTechniqueHowStepList;

  private OnHowFragmentInteractionListener mListener;

  public HowFragment() {
    // Required empty public constructor
  }

  public static HowFragment newInstance(String param1) {
    HowFragment fragment = new HowFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TECHNIQUE_ID, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mTechniqueId = getArguments().getString(ARG_TECHNIQUE_ID);
      Cursor cursor,testc;
      if (getActivity() != null) {
        testc = getActivity().getContentResolver()
            .query(TechniqueHowEntry.CONTENT_URI_TECHNIQUE_HOW, null,
                null, null,
                null);
        cursor = getActivity().getContentResolver()
            .query(TechniqueHowEntry.CONTENT_URI_TECHNIQUE_HOW, null,
                TechniqueHowEntry.COLUMN_NAME_TECHNIQUE_ID + "=?", new String[]{
                    mTechniqueId},
                null);
        if (cursor != null) {
          cursor.moveToFirst();
          testc.moveToFirst();
          Log.d("cursor", cursor.getCount()+"");
          Log.d("test cursor", testc.getCount()+"");
          String techniqueHowStep = cursor
              .getString(cursor.getColumnIndex(TechniqueHowEntry.COLUMN_NAME_STEPS));
          Gson gson = new Gson();
          Type type = new TypeToken<List<Step>>() {}.getType();
          mTechniqueHowStepList = gson.fromJson(techniqueHowStep, type);
          cursor.close();
        }
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_how, container, false);
    mTechniqueHowStepRecyclerView= rootView.findViewById(R.id.recycler_view_technique_how_steps);
    mTechniqueHowStepRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    mTechniquesHowBenefitRecyclerViewAdapter = new TechniquesHowStepRecyclerViewAdapter();
    mTechniqueHowStepRecyclerView.setAdapter(mTechniquesHowBenefitRecyclerViewAdapter);
    return rootView;
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onHowFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnHowFragmentInteractionListener) {
      mListener = (OnHowFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnHowFragmentInteractionListener");
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
   * activity..
   */
  public interface OnHowFragmentInteractionListener {
    void onHowFragmentInteraction(Uri uri);
  }

  public static class TechniquesHowStepRecyclerViewAdapter extends
      RecyclerView.Adapter<TechniquesHowStepRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_how_recycler_view_item,parent,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.mTechniqueHowStepTextView.setText(mTechniqueHowStepList.get(holder.getAdapterPosition()).getDesc());
    }

    @Override
    public int getItemCount() {
      if(mTechniqueHowStepList != null){
        return mTechniqueHowStepList.size();
      }
      return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      TextView mTechniqueHowStepTextView;
      public ViewHolder(View itemView) {
        super(itemView);
        mTechniqueHowStepTextView = itemView.findViewById(R.id.text_view_technique_how_step);
      }
    }
  }
}
