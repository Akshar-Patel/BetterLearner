package in.aternal.betterlearner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;


public class TechniqueDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "better-learner.db";
  public static final int DATABASE_VERSION = 1;

  public TechniqueDbHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

    final String SQL_CREATE_TECHNIQUE_TABLE = "CREATE TABLE " + TechniqueEntry.TABLE_NAME + " (" +
        TechniqueEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        TechniqueEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
        TechniqueEntry.COLUMN_NAME_DESC + " TEXT NOT NULL" +
        "); ";


    final String SQL_CREATE_TECHNIQUE_WHY_TABLE = "CREATE TABLE " + TechniqueWhyEntry.TABLE_NAME + " (" +
        TechniqueWhyEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        TechniqueWhyEntry.COLUMN_NAME_BENEFIT1 + " TEXT NOT NULL," +
        TechniqueWhyEntry.COLUMN_NAME_BENEFIT2 + " TEXT NOT NULL," +
        TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID + " INTEGER, " +
        " FOREIGN KEY (" + TechniqueWhyEntry.COLUMN_NAME_TECHNIQUE_ID +
        ") REFERENCES " +
        TechniqueEntry.TABLE_NAME + "(" + TechniqueEntry.COLUMN_NAME_ID + ")" +
        "); ";

    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_TABLE);
    sqLiteDatabase.execSQL(SQL_CREATE_TECHNIQUE_WHY_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //TODO: on upgrade db
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TechniqueWhyEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}
