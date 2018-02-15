package in.aternal.betterlearner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhatEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;
import in.aternal.betterlearner.model.TechniqueWhy.Benefit;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnWhyFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WhyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhyFragment extends Fragment {

  private static final String ARG_TECHNIQUE_ID = "arg_technique_id";

  private String mTechniqueId;
  private RecyclerView mTechniqueWhyBenefitRecyclerView;
  private TechniquesWhyBenefitRecyclerViewAdapter mTechniquesWhyBenefitRecyclerViewAdapter;
  private static List<Benefit> mTechniqueWhyBenefitList;

  private OnWhyFragmentInteractionListener mListener;

  public WhyFragment() {
    // Required empty public constructor
  }

  public static WhyFragment newInstance(String techniqueId) {
    WhyFragment fragment = new WhyFragment();
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
            .query(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, null,
                TechniqueWhatEntry.COLUMN_NAME_TECHNIQUE_ID + "=?", new String[]{
                    mTechniqueId},
                null);
        if (cursor != null) {
          cursor.moveToFirst();
          String techniqueWhyBenefit = cursor
              .getString(cursor.getColumnIndex(TechniqueWhyEntry.COLUMN_NAME_BENEFITS));
          Gson gson = new Gson();
          Type type = new TypeToken<List<Benefit>>() {}.getType();
          mTechniqueWhyBenefitList = gson.fromJson(techniqueWhyBenefit, type);
          cursor.close();
        }
      }
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_why, container, false);
    mTechniqueWhyBenefitRecyclerView = rootView.findViewById(R.id.recycler_view_technique_why_benefits);
    mTechniqueWhyBenefitRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    mTechniquesWhyBenefitRecyclerViewAdapter = new TechniquesWhyBenefitRecyclerViewAdapter();
    mTechniqueWhyBenefitRecyclerView.setAdapter(mTechniquesWhyBenefitRecyclerViewAdapter);
    return rootView;
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onWhyFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnWhyFragmentInteractionListener) {
      mListener = (OnWhyFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnWhyFragmentInteractionListener");
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
  public interface OnWhyFragmentInteractionListener {
    void onWhyFragmentInteraction(Uri uri);
  }

  public static class TechniquesWhyBenefitRecyclerViewAdapter extends
      RecyclerView.Adapter<TechniquesWhyBenefitRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_why_recycler_view_item,parent,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.mTechniqueWhyBenefitTextView.setText(mTechniqueWhyBenefitList.get(holder.getAdapterPosition()).getDesc());
    }

    @Override
    public int getItemCount() {
      if(mTechniqueWhyBenefitList != null){
        return mTechniqueWhyBenefitList.size();
      }
      return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      TextView mTechniqueWhyBenefitTextView;
      public ViewHolder(View itemView) {
        super(itemView);
        mTechniqueWhyBenefitTextView = itemView.findViewById(R.id.text_view_technique_why_benefit);
      }
    }
  }
}
