package in.aternal.betterlearner.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueHowEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhatEntry;
import in.aternal.betterlearner.data.TechniqueContract.TechniqueWhyEntry;


public class TechniqueContentProvider extends ContentProvider {

  public static final int TECHNIQUE = 100;
  public static final int TECHNIQUE_WITH_ID = 101;
  public static final int TECHNIQUE_WHAT = 200;
  public static final int TECHNIQUE_WHY = 300;
  public static final int TECHNIQUE_HOW = 400;

  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private static String TAG = TechniqueContentProvider.class.getSimpleName();
  private TechniqueDbHelper mTechniqueDbHelper;
  private SQLiteDatabase mSqLiteDatabase;

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(TechniqueContract.AUTHORITY, TechniqueContract.PATH_TECHNIQUE, TECHNIQUE);
    uriMatcher
        .addURI(TechniqueContract.AUTHORITY, TechniqueContract.PATH_TECHNIQUE + "/#",
            TECHNIQUE_WITH_ID);
    uriMatcher
        .addURI(TechniqueContract.AUTHORITY, TechniqueContract.PATH_TECHNIQUE_WHAT, TECHNIQUE_WHAT);
    uriMatcher
        .addURI(TechniqueContract.AUTHORITY, TechniqueContract.PATH_TECHNIQUE_WHY, TECHNIQUE_WHY);
    uriMatcher
        .addURI(TechniqueContract.AUTHORITY, TechniqueContract.PATH_TECHNIQUE_HOW, TECHNIQUE_HOW);
    return uriMatcher;
  }

  @Override
  public boolean onCreate() {
    mTechniqueDbHelper = new TechniqueDbHelper(getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    mSqLiteDatabase = mTechniqueDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);
    Cursor returnCursor = null;

    switch (match) {
      case TECHNIQUE:
        returnCursor = mSqLiteDatabase
            .query(TechniqueEntry.TABLE_NAME, projection, selection, selectionArgs, null, null,
                sortOrder);
        break;
      case TECHNIQUE_WITH_ID:
        String id = uri.getPathSegments().get(1);
        returnCursor = mSqLiteDatabase
            .query(TechniqueEntry.TABLE_NAME, projection, "id=?", new String[]{id}, null, null,
                sortOrder);
        break;
      case TECHNIQUE_WHAT:
        returnCursor = mSqLiteDatabase
            .query(TechniqueWhatEntry.TABLE_NAME, projection, selection, selectionArgs, null, null,
                sortOrder);
        break;
      case TECHNIQUE_WHY:
        returnCursor = mSqLiteDatabase
            .query(TechniqueWhyEntry.TABLE_NAME, projection, selection, selectionArgs, null, null,
                sortOrder);
        break;
      case TECHNIQUE_HOW:
        returnCursor = mSqLiteDatabase
            .query(TechniqueHowEntry.TABLE_NAME, projection, selection, selectionArgs, null, null,
                sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("unknown uri: " + uri);
    }

    if (returnCursor != null && getContext() != null) {
      returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
    }

    return returnCursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    mSqLiteDatabase = mTechniqueDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);

    Uri returnUri = null;
    switch (match) {
      case TECHNIQUE:
        long id = mSqLiteDatabase.insert(TechniqueEntry.TABLE_NAME, null, contentValues);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(TechniqueEntry.CONTENT_URI_TECHNIQUE, id);
        } else {
          Log.e(TAG, "failed to insert row into " + uri);
        }
        break;
      case TECHNIQUE_WHAT:
        long whatId = mSqLiteDatabase.insert(TechniqueWhatEntry.TABLE_NAME, null, contentValues);
        if (whatId > 0) {
          returnUri = ContentUris.withAppendedId(TechniqueEntry.CONTENT_URI_TECHNIQUE, whatId);
        } else {
          Log.e(TAG, "failed to insert row into " + uri);
        }
        break;
      case TECHNIQUE_WHY:
        long whyId = mSqLiteDatabase.insert(TechniqueWhyEntry.TABLE_NAME, null, contentValues);
        if (whyId > 0) {
          returnUri = ContentUris
              .withAppendedId(TechniqueWhyEntry.CONTENT_URI_TECHNIQUE_WHY, whyId);
        } else {
          Log.e(TAG, "failed to insert row into " + uri);
        }
        break;
      case TECHNIQUE_HOW:
        long howId = mSqLiteDatabase.insert(TechniqueHowEntry.TABLE_NAME, null, contentValues);
        Log.d("how id ",howId+"");
        if (howId > 0) {
          returnUri = ContentUris
              .withAppendedId(TechniqueHowEntry.CONTENT_URI_TECHNIQUE_HOW, howId);
        } else {
          Log.e(TAG, "failed to insert row into " + uri);
        }
        Log.d("how id ",howId+"");
        break;
      default:
        throw new UnsupportedOperationException("unknown uri: " + uri);
    }
    if (getContext() != null) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return returnUri;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
      @Nullable String selection,
      @Nullable String[] selectionArgs) {

    mSqLiteDatabase = mTechniqueDbHelper.getWritableDatabase();
    int updatedRows;
    int match = sUriMatcher.match(uri);

    switch (match) {
      case TECHNIQUE_WITH_ID:
        String id = uri.getPathSegments().get(1);
        updatedRows = mSqLiteDatabase
            .update(TechniqueEntry.TABLE_NAME, contentValues, "id=?", new String[]{id});
        if (getContext() != null) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d("id update ", id + " " + updatedRows);
        return updatedRows;
      case TECHNIQUE_WHAT:
        updatedRows = mSqLiteDatabase
            .update(TechniqueWhatEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (getContext() != null) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
      case TECHNIQUE_WHY:
        updatedRows = mSqLiteDatabase
            .update(TechniqueWhyEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (getContext() != null) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
      case TECHNIQUE_HOW:
        updatedRows = mSqLiteDatabase
            .update(TechniqueHowEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (getContext() != null) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
      default:
        throw new UnsupportedOperationException("unknown uri: " + uri);
    }
  }
}
