
package prathameshshetye.mark.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prathameshshetye.mark.Utilities.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "MARKERS";

    // Contacts table name
    private static final String TABLE_NAME = "markers";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
    private static final String KEY_DESCRIP = "_description";
    private static final String KEY_LAT = "_latitude";
    private static final String KEY_LNG = "_longitude";
    private static final String KEY_IMAGE = "_image";

    private static int notes;

    private static DatabaseHelper mInstance = null;

    public static DatabaseHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            Log.LogThis("Creating new Instance");
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MARKERS =
            "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_DESCRIP + " TEXT, "
                + KEY_LAT + " REAL, "
                + KEY_LNG + " REAL, "
                + KEY_IMAGE + " BLOB "
                + ")";
        db.execSQL(CREATE_MARKERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.LogThis("In onUpgrade -> oldVersion = " + oldVersion + " newVersion = " + newVersion);
    }

    // Adding new contact
    public int saveMarker(Marker marker) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, marker.getName()); // Name
        values.put(KEY_DESCRIP, marker.getDescrip()); // Description
        values.put(KEY_LAT, marker.getLatitude()); // Latitude
        values.put(KEY_LNG, marker.getLongitude()); // Longitude
        values.put(KEY_IMAGE, marker.getImage()); // Image

        // Inserting Row
        long id = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return (int)id;
    }

    // Getting single marker
    public Marker getMarker(long _id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_ID,
                KEY_LAT,
                KEY_LNG,
                KEY_NAME,
                KEY_DESCRIP,
                KEY_IMAGE
        }, KEY_ID + "=?", new String[]{
                String.valueOf(_id)
        }, null, null, null, null);
        if (cursor == null) {
            return new Marker();
        } else {
            cursor.moveToFirst();
        }

        Marker marker = new Marker(
                cursor.getInt(0),
                cursor.getFloat(1),
                cursor.getFloat(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getBlob(5)
        );
        cursor.close();
        return marker;
    }

    // Getting All Notifications
    public List<Marker> getAllMarkers() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Marker> markerlist = new ArrayList<Marker>();

        String selectQuery = "SELECT "
                + KEY_ID
                + KEY_LAT
                + KEY_LNG
                + KEY_NAME
                + KEY_DESCRIP
                + KEY_IMAGE + " FROM "
                + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                markerlist.add(new Marker(
                        cursor.getInt(0),
                        cursor.getFloat(1),
                        cursor.getFloat(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getBlob(5)
                ));
            } while (cursor.moveToNext());
        }

        // return contact list
        return markerlist;
    }

    // Getting Markers Count
    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.getCount();
    }

    // Updating Single Marker
    public int updateMarker(Marker marker) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.LogThis("Updating Marker" + marker.getID());

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, marker.getName()); // Name
        values.put(KEY_DESCRIP, marker.getDescrip()); // Description
        values.put(KEY_LAT, marker.getLatitude()); // Latitude
        values.put(KEY_LNG, marker.getLongitude()); // Longitude
        values.put(KEY_IMAGE, marker.getImage()); // Image

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{
                String.valueOf(marker.getID())
        });
    }

    // Deleting Single Marker
    public void deleteMarker(Marker marker) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{
                String.valueOf(marker.getID())
        });
        db.close();
    }

    //Searching notes based on marker content
    public List<Marker> searchMarkers(String query) {
        String q = query.replaceAll("'", "''");
        SQLiteDatabase db = this.getReadableDatabase();
        List<Marker> markerList = new ArrayList<Marker>();
        String selectQuery = "SELECT  "
                + KEY_ID + ","
                + KEY_LAT + ","
                + KEY_LNG + ","
                + KEY_NAME + ","
                + KEY_DESCRIP + ","
                + KEY_IMAGE
                + " FROM " + TABLE_NAME
                + " WHERE UPPER(" + KEY_NAME + ") LIKE UPPER('%" + q + "%') "
                + " OR UPPER(" + KEY_DESCRIP + ") LIKE UPPER('%" + q + "%') ";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                markerList.add(new Marker(
                        cursor.getInt(0),
                        cursor.getFloat(1),
                        cursor.getFloat(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getBlob(5)
                ));
            } while (cursor.moveToNext());
        }

        Log.LogThis("markerList,SIZE = " + markerList.size());
        // return contact list
        return markerList;
    }
}
