package in.aternal.betterlearner.background;


import static in.aternal.betterlearner.BuildConfig.DATA_URL;

import android.content.Context;
import android.content.SharedPreferences;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import in.aternal.betterlearner.BuildConfig;
import in.aternal.betterlearner.R;
import in.aternal.betterlearner.model.Technique;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

  private static final OkHttpClient client = new OkHttpClient();
  private static final Moshi moshi = new Moshi.Builder().build();

  private static final String UPDATE_URL = BuildConfig.UPDATE_URL;

  private static final JsonAdapter<List<Technique>> sListJsonAdapter = moshi.adapter(
      Types.newParameterizedType(List.class, Technique.class));
  List<Technique> mTechniqueList;

  public static List<Technique> getTechniqueList(Context context) {

      Request request = new Request.Builder()
          .url(DATA_URL)
          .build();
      Response response;
      try {
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
          throw new IOException("Unexpected code " + response);
        }
        return sListJsonAdapter.fromJson(response.body().source());
      } catch (IOException e) {
        e.printStackTrace();
      }
    return new ArrayList<Technique>();
  }

  public static Boolean isUpdateAvailable(Context context){

    SharedPreferences sharedPref = context.getSharedPreferences(
        context.getString(R.string.pref_file_update), Context.MODE_PRIVATE);
    int versionSharedPref = sharedPref.getInt(context.getString(R.string.pref_key_version),0);

    int versionJson = 0;
    Request request = new Request.Builder()
        .url(UPDATE_URL)
        .build();
    Response response;
    try {
      response = client.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      try {
        JSONObject jsonObject = new JSONObject(response.body().string());
        versionJson = jsonObject.getInt("version");
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if(versionJson > versionSharedPref){
      sharedPref.edit().putInt(context.getString(R.string.pref_key_version), versionJson).apply();
      return true;
    }
    return false;
  }
}
