package in.aternal.betterlearner.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TechniqueContract {

  //private constructor to prevent accident instantiation of class
  private TechniqueContract() {
  }

  public static final String AUTHORITY = "in.aternal.betterlearner";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  public static final String PATH_TECHNIQUE = "techniques";
  public static final String PATH_TECHNIQUE_WHY = "techniques_why";


  public static final class TechniqueEntry implements BaseColumns {

    public static final Uri CONTENT_URI_TECHNIQUE = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE)
        .build();

    public static final String TABLE_NAME = "technique";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DESC = "desc";
  }

  public static final class TechniqueWhyEntry implements BaseColumns {
    public static final Uri CONTENT_URI_TECHNIQUE_WHY = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE_WHY)
        .build();

    public static final String TABLE_NAME = "technique_why";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_BENEFIT1 = "benefit1";
    public static final String COLUMN_NAME_BENEFIT2 = "benefit2";
    public static final String COLUMN_NAME_BENEFIT3 = "benefit3";
    public static final String COLUMN_NAME_BENEFIT4 = "benefit4";
    public static final String COLUMN_NAME_BENEFIT5 = "benefit5";
    public static final String COLUMN_NAME_BENEFIT6 = "benefit6";
    public static final String COLUMN_NAME_BENEFIT7 = "benefit7";
    public static final String COLUMN_NAME_BENEFIT8 = "benefit8";
    public static final String COLUMN_NAME_BENEFIT9 = "benefit9";
    public static final String COLUMN_NAME_BENEFIT10 = "benefit10";
    public static final String COLUMN_NAME_TECHNIQUE_ID = "technique_id";
  }

}
