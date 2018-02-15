package in.aternal.betterlearner.background;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueHowEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhatEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;
import in.aternal.betterlearner.model.Technique;
import in.aternal.betterlearner.model.TechniqueHow;
import in.aternal.betterlearner.model.TechniqueHow.Step;
import in.aternal.betterlearner.model.TechniqueWhy.Benefit;
import java.util.List;


public class TechniquePullService extends IntentService {

  public TechniquePullService() {
    super("TechniquePullService");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    if (JsonParser.isUpdateAvailable(getApplicationContext())) {

      List<Technique> techniqueList = JsonParser.getTechniqueList(getApplicationContext());

      ContentValues contentValuesTechnique;
      ContentValues contentValuesTechniqueWhat;
      ContentValues contentValuesTechniqueWhy;
      ContentValues contentValuesTechniqueHow;

      for (Technique technique : techniqueList) {

        contentValuesTechnique = new ContentValues();
        contentValuesTechnique.put(TechniqueEntry.COLUMN_NAME_ID, technique.getId());
        contentValuesTechnique.put(TechniqueEntry.COLUMN_NAME_NAME, technique.getName());
        contentValuesTechnique.put(TechniqueEntry.COLUMN_NAME_DESC, technique.getDesc());

        contentValuesTechniqueWhat = new ContentValues();
        contentValuesTechniqueWhat
            .put(TechniqueWhatEntry.COLUMN_NAME_DESC, technique.getTechniqueWhat().getDesc());
        contentValuesTechniqueWhat
            .put(TechniqueWhatEntry.COLUMN_NAME_TECHNIQUE_ID, technique.getId());

        contentValuesTechniqueWhy = new ContentValues();
        List<Benefit> benefitList = technique.getTechniqueWhy().getBenefitList();
        Gson gson = new Gson();
        String benefitString = gson.toJson(benefitList);
        contentValuesTechniqueWhy.put(TechniqueWhyEntry.COLUMN_NAME_BENEFITS, benefitString);
        contentValuesTechniqueWhy
            .put(TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID, technique.getId());

        contentValuesTechniqueHow = new ContentValues();
        List<Step> stepList = technique.getTechniqueHow().getStepList();
        String stepString = gson.toJson(stepList);
        contentValuesTechniqueHow.put(TechniqueHowEntry.COLUMN_NAME_STEPS, stepString);
        contentValuesTechniqueHow
            .put(TechniqueHowEntry.COLUMN_NAME_TECHNIQUE_ID, technique.getId());

        if (isRecordPresent(technique.getId())) {
          Uri uriToQueryIdTechnique = TechniqueEntry.CONTENT_URI_TECHNIQUE.buildUpon()
              .appendPath(String.valueOf(technique.getId())).build();
          getContentResolver().update(uriToQueryIdTechnique, contentValuesTechnique, null, null);

          int techniqueWhatId = getTechniqueWhatId(technique.getId());
          Uri uriToQueryIdTechniqueWhat = TechniqueWhatEntry.CONTENT_URI_TECHNIQUE_WHAT.buildUpon()
              .appendPath(String.valueOf(technique.getId())).build();
//          getContentResolver()
//              .update(uriToQueryIdTechniqueWhat, contentValuesTechniqueWhat, "id=?", new String[]{
//                  String.valueOf(techniqueWhatId)});
        } else {
          getContentResolver().insert(TechniqueEntry.CONTENT_URI_TECHNIQUE, contentValuesTechnique);
          getContentResolver()
              .insert(TechniqueWhatEntry.CONTENT_URI_TECHNIQUE_WHAT, contentValuesTechniqueWhat);
          getContentResolver()
              .insert(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, contentValuesTechniqueWhy);
          getContentResolver()
              .insert(TechniqueHowEntry.CONTENT_URI_TECHNIQUE_HOW, contentValuesTechniqueHow);
        }
      }
    }
  }

  private Boolean isRecordPresent(int id) {
    Uri uriToQueryId = TechniqueEntry.CONTENT_URI_TECHNIQUE.buildUpon()
        .appendPath(String.valueOf(id))
        .build();
    Cursor cursor = getContentResolver()
        .query(uriToQueryId, null,
            null, null, null);
    if (cursor != null) {
      int count = cursor.getCount();
      cursor.close();
      if (count > 0) {
        return true;
      }
    }
    return false;
  }

  private int getTechniqueWhatId(int techniqueId) {
    Cursor cursor = getContentResolver()
        .query(TechniqueWhatEntry.CONTENT_URI_TECHNIQUE_WHAT, null,
            "technique_id=?", new String[]{String.valueOf(techniqueId)}, null);
    int id = 0;
    if (cursor != null) {
      cursor.moveToFirst();
      id = cursor.getInt(cursor.getColumnIndex(TechniqueWhatEntry.COLUMN_NAME_ID));
      cursor.close();
    }
    return id;
  }

  private void testWhat(int id) {
    Cursor cursor = getContentResolver()
        .query(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, null,
            "technique_id=?", new String[]{String.valueOf(id)}, null);
    if (cursor != null) {
      int count = cursor.getCount();
      cursor.close();
      Log.d("what count", count + "");
    }
  }
}
