package app.kosanworks.smsgateway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghost on 05/06/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "sms_gateway";

    private static final String TABLE = "data";

    //    private static final String KEY_ = "";

    private static final String KEY_ID = "id";
    private static final String KEY_FROM = "dari";
    private static final String KEY_MESSAGE = "pesan";
    private static final String KEY_DATE = "tanggal";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_JADWAL = "CREATE TABLE "+TABLE+"("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FROM + " TEXT,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE_JADWAL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    public boolean add(Pesan p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FROM, p.getFrom());
        values.put(KEY_MESSAGE, p.getMessage());
        values.put(KEY_DATE, p.date);

        db.insert(TABLE, null, values);

        db.close();
        Log.d(TAG, "Berhasil menambahkan");

        return true;
    }

    public List<Pesan> getAll(){
        List<Pesan> list = new ArrayList<>();
        String sq;
//        if (date.equals("null")){
            sq = "SELECT * FROM "+ TABLE + " ORDER BY id DESC;";
//        }else{
//            sq = "SELECT * FROM " + TABLE + " WHERE "+ date;
//        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sq,null);

        if (cursor.moveToFirst()){
            do {
                Pesan p = new Pesan();
                p.setId(cursor.getString(0));
                p.setFrom(cursor.getString(1));
                p.setMessage(cursor.getString(2));
                p.setDate(cursor.getString(3));

                list.add(p);
            }while (cursor.moveToNext());
        }
        Log.d("data", list.toString());
        return list;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE+" WHERE id = "+id);
    }
}