package in.aternal.betterlearner.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TechniqueContract {

  //private constructor to prevent accident instantiation of class
  private TechniqueContract() {
  }

  public static final String AUTHORITY = "in.aternal.betterlearner";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  public static final String PATH_TECHNIQUE = "technique";
  public static final String PATH_TECHNIQUE_WHAT = "technique_what";
  public static final String PATH_TECHNIQUE_WHY = "technique_why";
  public static final String PATH_TECHNIQUE_HOW = "technique_how";


  public static final class TechniqueEntry implements BaseColumns {

    public static final Uri CONTENT_URI_TECHNIQUE = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE)
        .build();

    public static final String TABLE_NAME = "technique";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DESC = "desc";
  }

  public static final class TechniqueWhatEntry implements BaseColumns {
    public static final Uri CONTENT_URI_TECHNIQUE_WHAT = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE_WHAT)
        .build();

    public static final String TABLE_NAME = "technique_what";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_DESC = "desc";
    public static final String COLUMN_NAME_TECHNIQUE_ID = "technique_id";
  }

  public static final class TechniqueWhyEntry implements BaseColumns {

    public static final Uri CONTENT_URI_TECHNIQUE_WHY = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE_WHY)
        .build();

    public static final String TABLE_NAME = "technique_why";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_BENEFITS = "benefits";
    public static final String COLUMN_NAME_TECHNIQUE_ID = "technique_id";
  }

  public static final class TechniqueHowEntry implements BaseColumns {

    public static final Uri CONTENT_URI_TECHNIQUE_HOW = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_TECHNIQUE_HOW)
        .build();

    public static final String TABLE_NAME = "technique_how";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_STEPS = "steps";
    public static final String COLUMN_NAME_TECHNIQUE_ID = "technique_id";
  }

}
