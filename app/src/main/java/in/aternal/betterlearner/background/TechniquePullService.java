package in.aternal.betterlearner.background;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;
import in.aternal.betterlearner.model.Technique;
import java.util.List;


public class TechniquePullService extends IntentService {

  public TechniquePullService() {
    super("TechniquePullService");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    if (JsonParser.isUpdateAvailable(getApplicationContext())) {
      List<Technique> techniqueList = JsonParser.getTechniqueList(getApplicationContext());

      testWhat(1);
      ContentValues contentValues;
      for (Technique technique :
          techniqueList) {

        contentValues = new ContentValues();
        contentValues.put(TechniqueEntry.COLUMN_NAME_ID, technique.getId());
        contentValues.put(TechniqueEntry.COLUMN_NAME_NAME, technique.getName());
        contentValues.put(TechniqueEntry.COLUMN_NAME_DESC, technique.getDesc());

        if (isRecordPresent(technique.getId())) {
          Uri uriToQueryId = TechniqueEntry.CONTENT_URI_TECHNIQUE.buildUpon()
              .appendPath(String.valueOf(technique.getId())).build();
          getContentResolver().update(uriToQueryId, contentValues, null, null);
        } else {
          getContentResolver().insert(TechniqueEntry.CONTENT_URI_TECHNIQUE, contentValues);
        }
      }
    }
  }

  private Boolean isRecordPresent(int id) {
    Uri uriToQueryId = TechniqueEntry.CONTENT_URI_TECHNIQUE.buildUpon().appendPath(String.valueOf(id))
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

  private void testWhat(int id){
    Cursor cursor = getContentResolver()
        .query(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, null,
            "technique_id=?", new String[]{String.valueOf(id)}, null);
    if (cursor != null) {
      int count = cursor.getCount();
      cursor.close();
      Log.d("what count",count+"");
    }
  }
}
