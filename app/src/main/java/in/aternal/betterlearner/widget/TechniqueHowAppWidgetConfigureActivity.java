package in.aternal.betterlearner.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import in.aternal.betterlearner.R;

/**
 * The configuration screen for the {@link TechniqueHowAppWidget TechniqueHowAppWidget} AppWidget.
 */
public class TechniqueHowAppWidgetConfigureActivity extends Activity {

  private static final String PREFS_NAME = "in.aternal.betterlearner.widget.TechniqueHowAppWidget";
  private static final String PREF_PREFIX_KEY = "appwidget_";
  static int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

  RecyclerView mTechniqueNameRecyclerView;
  TechniqueRecyclerViewAdapter mTechniqueNameRecyclerViewAdapter;

  EditText mAppWidgetText;

  public TechniqueHowAppWidgetConfigureActivity() {
    super();
  }

  // Write the prefix to the SharedPreferences object for this widget
  static void saveTitlePref(Context context, int appWidgetId, String text) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
    prefs.apply();
  }

  // Read the prefix from the SharedPreferences object for this widget.
  // If there is no preference saved, get the default from a resource
  static String loadTitlePref(Context context, int appWidgetId) {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
    String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
    if (titleValue != null) {
      return titleValue;
    } else {
      return context.getString(R.string.appwidget_text);
    }
  }

  static void deleteTitlePref(Context context, int appWidgetId) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.remove(PREF_PREFIX_KEY + appWidgetId);
    prefs.apply();
  }

  // Write the prefix to the SharedPreferences object for this widget
  static void saveTechniqueNamePref(Context context, int appWidgetId, String text) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.putString(PREF_PREFIX_KEY + appWidgetId + "technique_name", text);
    prefs.apply();
  }

  // Read the prefix from the SharedPreferences object for this widget.
  // If there is no preference saved, get the default from a resource
  static String loadTechniqueNamePref(Context context, int appWidgetId) {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
    String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + "technique_name", null);
    if (titleValue != null) {
      return titleValue;
    } else {
      return context.getString(R.string.appwidget_text);
    }
  }

  static void deleteTechniqueNamePref(Context context, int appWidgetId) {
    SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
    prefs.remove(PREF_PREFIX_KEY + appWidgetId + "technique_name");
    prefs.apply();
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    // Set the result to CANCELED.  This will cause the widget host to cancel
    // out of the widget placement if the user presses the back button.
    setResult(RESULT_CANCELED);

    setContentView(R.layout.technique_how_app_widget_configure);
    mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);

    // Find the widget id from the intent.
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if (extras != null) {
      mAppWidgetId = extras.getInt(
          AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // If this activity was started with an intent without an app widget ID, finish with an error.
    if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
      finish();
      return;
    }

    mAppWidgetText
        .setText(loadTitlePref(TechniqueHowAppWidgetConfigureActivity.this, mAppWidgetId));

    mTechniqueNameRecyclerView = findViewById(R.id.widget_recycler_view_technique);
    mTechniqueNameRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mTechniqueNameRecyclerViewAdapter = new TechniqueRecyclerViewAdapter(this);
    mTechniqueNameRecyclerView.setAdapter(mTechniqueNameRecyclerViewAdapter);
  }
}

