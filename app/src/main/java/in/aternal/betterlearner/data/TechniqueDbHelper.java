package in.aternal.betterlearner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueHowEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhatEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;


class TechniqueDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "better-learner.db";
  private static final int DATABASE_VERSION = 1;

  public TechniqueDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

    final String SQL_CREATE_TECHNIQUE_TABLE = "CREATE TABLE " + TechniqueEntry.TABLE_NAME + " (" +
        TechniqueEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        TechniqueEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
        TechniqueEntry.COLUMN_NAME_DESC + " TEXT NOT NULL" +
        "); ";

    final String SQL_CREATE_TECHNIQUE_WHAT_TABLE =
        "CREATE TABLE " + TechniqueWhatEntry.TABLE_NAME + " (" +
            TechniqueWhatEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TechniqueWhatEntry.COLUMN_NAME_DESC + " TEXT NOT NULL," +
            TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID + " INTEGER, " +
            " FOREIGN KEY (" + TechniqueWhatEntry.COLUMN_NAME_TECHNIQUE_ID +
            ") REFERENCES " +
            TechniqueEntry.TABLE_NAME + "(" + TechniqueEntry.COLUMN_NAME_ID + ")" +
            "); ";

    final String SQL_CREATE_TECHNIQUE_WHY_TABLE =
        "CREATE TABLE " + TechniqueWhyEntry.TABLE_NAME + " (" +
            TechniqueWhyEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TechniqueWhyEntry.COLUMN_NAME_BENEFITS + " TEXT NOT NULL," +
            TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID + " INTEGER, " +
            " FOREIGN KEY (" + TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID +
            ") REFERENCES " +
            TechniqueEntry.TABLE_NAME + "(" + TechniqueEntry.COLUMN_NAME_ID + ")" +
            "); ";

    final String SQL_CREATE_TECHNIQUE_HOW_TABLE =
        "CREATE TABLE " + TechniqueHowEntry.TABLE_NAME + " (" +
            TechniqueHowEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TechniqueHowEntry.COLUMN_NAME_STEPS + " TEXT NOT NULL," +
            TechniqueHowEntry.COLUMN_NAME_TECHNIQUE_ID + " INTEGER, " +
            " FOREIGN KEY (" + TechniqueHowEntry.COLUMN_NAME_TECHNIQUE_ID +
            ") REFERENCES " +
            TechniqueEntry.TABLE_NAME + "(" + TechniqueEntry.COLUMN_NAME_ID + ")" +
            "); ";

    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_TABLE);
    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_WHAT_TABLE);
    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_WHY_TABLE);
    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_HOW_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueWhatEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueWhyEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueHowEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}
