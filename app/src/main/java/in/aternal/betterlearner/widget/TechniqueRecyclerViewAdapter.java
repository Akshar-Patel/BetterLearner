package in.aternal.betterlearner.widget;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import in.aternal.betterlearner.R;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueHowEntry;
import in.aternal.betterlearner.model.TechniqueHow.Step;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TechniqueRecyclerViewAdapter extends
    RecyclerView.Adapter<TechniqueRecyclerViewAdapter.ViewHolder> {

  private final Activity mActivity;
  private final List<String> mTechniqueNameList;

  public TechniqueRecyclerViewAdapter(Activity activity) {
    mTechniqueNameList = new ArrayList<>();
    mActivity = activity;
    Cursor cursor = mActivity.getContentResolver()
        .query(TechniqueEntry.CONTENT_URI_TECHNIQUE, null, null, null, null);
    if (cursor != null) {
      while (cursor.moveToNext()) {
        mTechniqueNameList
            .add(cursor.getString(cursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_NAME)));
      }
      cursor.close();
    }

  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.technique_how_app_widget_configure_recycler_view_item, parent,
            false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTechniqueNameTextView.setText(mTechniqueNameList.get(position));
    holder.itemView.setTag(position);
  }

  @Override
  public int getItemCount() {
    if (mTechniqueNameList != null) {
      return mTechniqueNameList.size();
    }
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    final TextView mTechniqueNameTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      mTechniqueNameTextView = itemView.findViewById(R.id.text_view_technique_name_widget);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      int techniqueId = (int) view.getTag();

      String widgetText = "";
      String techniqueHowStep = null;
      Cursor cursor = view.getContext().getContentResolver()
          .query(TechniqueHowEntry.CONTENT_URI_TECHNIQUE_HOW, null,
              TechniqueHowEntry.COLUMN_NAME_TECHNIQUE_ID + "=?", new String[]{
                  String.valueOf(techniqueId)}, null);
      if (cursor != null) {
        cursor.moveToFirst();
        techniqueHowStep = cursor
            .getString(cursor.getColumnIndex(TechniqueHowEntry.COLUMN_NAME_STEPS));
        cursor.close();
      }


      List<Step> stepList;
      Gson gson = new Gson();
      Type type = new TypeToken<List<Step>>() {
      }.getType();
      stepList = gson.fromJson(techniqueHowStep, type);

      StringBuilder sb = new StringBuilder();
      int count = 1;
      if (stepList != null) {
        for (Step step : stepList) {
          sb.append(count++);
          sb.append(". ");
          sb.append(step.getDesc());
          sb.append("\n");
        }
      }

      widgetText = sb.toString();

      Cursor techniqueCursor = view.getContext().getContentResolver()
          .query(TechniqueEntry.CONTENT_URI_TECHNIQUE, null, "id=?", new String[]{
              String.valueOf(techniqueId)}, null);
      String techniqueName = null;
      if (techniqueCursor != null) {
        techniqueCursor.moveToFirst();
        techniqueName = techniqueCursor
            .getString(techniqueCursor.getColumnIndex(TechniqueEntry.COLUMN_NAME_NAME));
        techniqueCursor.close();
      }

      TechniqueHowAppWidgetConfigureActivity
          .saveTechniqueNamePref(mActivity, TechniqueHowAppWidgetConfigureActivity.mAppWidgetId,
              techniqueName);
      TechniqueHowAppWidgetConfigureActivity
          .saveTitlePref(mActivity, TechniqueHowAppWidgetConfigureActivity.mAppWidgetId,
              widgetText);

      // It is the responsibility of the configuration activity to update the app widget
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(view.getContext());
      TechniqueHowAppWidget.updateAppWidget(mActivity, appWidgetManager,
          TechniqueHowAppWidgetConfigureActivity.mAppWidgetId);

      // Make sure we pass back the original appWidgetId
      Intent resultValue = new Intent();
      resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
          TechniqueHowAppWidgetConfigureActivity.mAppWidgetId);
      mActivity.setResult(RESULT_OK, resultValue);
      mActivity.finish();
    }
  }
}
