package in.aternal.betterlearner.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import in.aternal.betterlearner.R;
import in.aternal.betterlearner.background.TechniquePullService;
import in.aternal.betterlearner.background.TechniquePullServiceReceiver;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;

public class MainActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {

  public static final String EXTRA_TECHNIQUE_ID = "extra_technique_id";
  private RecyclerView mTechniqueRecyclerView;
  private BroadcastReceiver mBroadcastReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    IntentFilter filter = new IntentFilter(TechniquePullServiceReceiver.ACTION_DATA_FETCH);
    mBroadcastReceiver = new TechniquePullServiceReceiver();
    registerReceiver(mBroadcastReceiver, filter);

    mTechniqueRecyclerView = findViewById(R.id.recycler_view_technique);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      gridLayoutManager.setSpanCount(3);
    }
    mTechniqueRecyclerView.setLayoutManager(gridLayoutManager);

    Intent intentTechniquePullService = new Intent(this, TechniquePullService.class);
    this.startService(intentTechniquePullService);

    getSupportLoaderManager().initLoader(0, null, this);

    AdView adView = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);

    FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this, TechniqueEntry.CONTENT_URI_TECHNIQUE,
        null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    TechniquesRecyclerViewAdapter techniqueRecyclerViewAdapter = new TechniquesRecyclerViewAdapter(
        data, loader.getContext());
    mTechniqueRecyclerView.setAdapter(techniqueRecyclerViewAdapter);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(mBroadcastReceiver);
  }

  public static class TechniquesRecyclerViewAdapter extends
      RecyclerView.Adapter<TechniquesRecyclerViewAdapter.ViewHolder> {

    final Cursor mCursor;
    final Context mContext;

    public TechniquesRecyclerViewAdapter(Cursor cursor, Context context) {
      mCursor = cursor;
      mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.activity_main_recycler_view_item, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      mCursor.moveToPosition(position);
      holder.mTechniqueNameTextView
          .setText(mCursor.getString(mCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_NAME)));
      holder.mTechniqueDescTextView
          .setText(mCursor.getString(mCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_DESC)));
    }

    @Override
    public int getItemCount() {
      if (mCursor != null) {
        return mCursor.getCount();
      }
      return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

      final TextView mTechniqueNameTextView;
      final TextView mTechniqueDescTextView;

      public ViewHolder(View itemView) {
        super(itemView);
        mTechniqueNameTextView = itemView.findViewById(R.id.text_view_technique_name);
        mTechniqueDescTextView = itemView.findViewById(R.id.text_view_technique_desc);
        itemView.setOnClickListener(this);
      }

      @Override
      public void onClick(View view) {
        Intent intentTechniqueDetail = new Intent(view.getContext(), TechniqueDetailActivity.class);
        intentTechniqueDetail.putExtra(EXTRA_TECHNIQUE_ID, getLayoutPosition());
        view.getContext().startActivity(intentTechniqueDetail);
      }
    }
  }
}
