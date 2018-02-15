package in.aternal.betterlearner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import in.aternal.betterlearner.background.TechniquePullService;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;
import in.aternal.betterlearner.data.TechniqueDbHelper;

public class MainActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {

  RecyclerView mTechniqueRecyclerView;
  TechniquesRecyclerViewAdapter mTechniqueRecyclerViewAdapter;
  private GridLayoutManager mGridLayoutManager;
  public static final String EXTRA_TECHNIQUE_ID = "extra_technique_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //clearData();
    //fakeInsert(0);
    //fakeInsert(1);
    //fakeInsertWhat(1);

    mTechniqueRecyclerView = findViewById(R.id.recycler_view_technique);
    mGridLayoutManager = new GridLayoutManager(this, 1);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      mGridLayoutManager.setSpanCount(3);
    }
    mTechniqueRecyclerView.setLayoutManager(mGridLayoutManager);

    Intent intentTechniquePullService = new Intent(this, TechniquePullService.class);
    this.startService(intentTechniquePullService);

    getSupportLoaderManager().initLoader(0, null, this);

  }

  void fakeInsert(int id) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TechniqueEntry.COLUMN_NAME_ID, id);
    contentValues.put(TechniqueEntry.COLUMN_NAME_NAME, "pomo");
    contentValues.put(TechniqueEntry.COLUMN_NAME_DESC, "pomodesc");
    getContentResolver().insert(TechniqueEntry.CONTENT_URI_TECHNIQUE, contentValues);
  }
  void fakeInsertWhat(int id) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TechniqueWhyEntry.COLUMN_NAME_ID, id);
    contentValues.put(TechniqueWhyEntry.COLUMN_NAME_BENEFIT1, "bf1");
    contentValues.put(TechniqueWhyEntry.COLUMN_NAME_BENEFIT2, "bf2");
    contentValues.put(TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID,1);
    getContentResolver().insert(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, contentValues);
  }


  void clearData(){
    TechniqueDbHelper techniqueDbHelper = new TechniqueDbHelper(this);
    SQLiteDatabase sqLiteDatabase = techniqueDbHelper.getWritableDatabase();
    sqLiteDatabase.execSQL("delete from "+TechniqueEntry.TABLE_NAME);
    sqLiteDatabase.close();
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this, TechniqueEntry.CONTENT_URI_TECHNIQUE,
        null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mTechniqueRecyclerViewAdapter = new TechniquesRecyclerViewAdapter(data, loader.getContext());
    mTechniqueRecyclerView.setAdapter(mTechniqueRecyclerViewAdapter);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }

  public static class TechniquesRecyclerViewAdapter extends
      RecyclerView.Adapter<TechniquesRecyclerViewAdapter.ViewHolder> {

    Cursor mCursor;
    Context mContext;

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
      holder.mTechniqueNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_NAME)));
      holder.mTechniqueDescTextView.setText(mCursor.getString(mCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_DESC)));
    }

    @Override
    public int getItemCount() {
      if (mCursor != null) {
        return mCursor.getCount();
      }
      return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


      TextView mTechniqueNameTextView;
      TextView mTechniqueDescTextView;

      public ViewHolder(View itemView) {
        super(itemView);
        mTechniqueNameTextView = itemView.findViewById(R.id.text_view_technique_name);
        mTechniqueDescTextView = itemView.findViewById(R.id.text_view_technique_desc);
        itemView.setOnClickListener(this);
      }

      @Override
      public void onClick(View view) {
        Intent intentTechniqueDetail = new Intent(view.getContext(), TechniqueDetailActivity.class);
        intentTechniqueDetail.putExtra(EXTRA_TECHNIQUE_ID,getLayoutPosition());
        view.getContext().startActivity(intentTechniqueDetail);
      }
    }
  }
}
