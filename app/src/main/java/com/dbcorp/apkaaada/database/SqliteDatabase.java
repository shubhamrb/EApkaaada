package com.dbcorp.apkaaada.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbcorp.apkaaada.model.UserCurrentAddress;
import com.dbcorp.apkaaada.model.UserDetails;

/**
 * Created by Bhupesh Sen on 23-01-2021.
 */
 public  class SqliteDatabase extends SQLiteOpenHelper {
    private final String TABLE_LOGIN = "userLogin";
    private final String TABLE_ADDRESS = "userAddress";
    private final String LOGIN_KEY_PHOTO = "photo";
    private final String LOGIN_KEY_NAME = "name";
    private final String LOGIN_KEY_SK = "sk";
    private final String LOGIN_KEY_USER_ID = "user_id";
    private final String LOGIN_KEY_EMAIL = "email";
    private final String LOGIN_KEY_PHONE = "phone";
    private final String LOGIN_KEY_HASH1 = "hash1";
    private final String LOGIN_KEY_HASH2 = "hash2";
    private final String ADDRESS_CURRENT_ADDRESS = "address";
    private final String ADDRESS_CURRENT_MOBILE = "mobile";
    private final String ADDRESS_CURRENT_LATITUDE = "latitude";
    private final String ADDRESS_CURRENT_LONGITUDE= "longitude";
    public SqliteDatabase(Context context) {
        super(context, "apkaada", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sb = String.format("CREATE TABLE IF NOT EXISTS %s(%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR);", TABLE_LOGIN, LOGIN_KEY_NAME, LOGIN_KEY_EMAIL,LOGIN_KEY_PHONE,LOGIN_KEY_PHOTO, LOGIN_KEY_HASH1,LOGIN_KEY_HASH2,LOGIN_KEY_SK,LOGIN_KEY_USER_ID);
        db.execSQL(sb);
        String address = String.format("CREATE TABLE IF NOT EXISTS %s(%s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR);", TABLE_ADDRESS, ADDRESS_CURRENT_MOBILE, ADDRESS_CURRENT_ADDRESS,ADDRESS_CURRENT_LATITUDE,ADDRESS_CURRENT_LONGITUDE);
        db.execSQL(address);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);


    }

   public void removeLoginUser() {
        SQLiteDatabase db = getDB();
        db.execSQL("DELETE FROM " + TABLE_LOGIN);
    }

    public void addLogin(UserDetails login) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_KEY_NAME, login.getName());
        cv.put(LOGIN_KEY_EMAIL, login.getEmail());
        cv.put(LOGIN_KEY_PHONE, login.getNumber());
        cv.put(LOGIN_KEY_PHOTO, login.getPhoto());
        cv.put(LOGIN_KEY_SK, login.getSk());
        cv.put(LOGIN_KEY_USER_ID, login.getUserId());
        cv.put(LOGIN_KEY_HASH1, "");
        cv.put(LOGIN_KEY_HASH2, "");
        db.insert(TABLE_LOGIN, null, cv);
    }
    public void addADDRESS(UserCurrentAddress userCurrentAddress) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(ADDRESS_CURRENT_MOBILE, userCurrentAddress.getMobile());
        cv.put(ADDRESS_CURRENT_ADDRESS, userCurrentAddress.getAddress());
        cv.put(ADDRESS_CURRENT_LATITUDE, userCurrentAddress.getLatitude());
        cv.put(ADDRESS_CURRENT_LONGITUDE, userCurrentAddress.getLongitude());
        db.insert(TABLE_ADDRESS, null, cv);
    }

    public void updateLogin(UserDetails login) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_KEY_NAME, login.getName());
        cv.put(LOGIN_KEY_EMAIL, login.getEmail());
        db.update(TABLE_LOGIN, cv,  "phone = ?", new String[]{login.getNumber()});
    }

    public UserDetails getLogin() {
        UserDetails login = null;
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN, null);
        if (cursor.moveToNext()) {
            login = new UserDetails(cursor.getString(cursor.getColumnIndex(LOGIN_KEY_USER_ID)),cursor.getString(cursor.getColumnIndex(LOGIN_KEY_SK)),cursor.getString(cursor.getColumnIndex(LOGIN_KEY_NAME)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_EMAIL)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHONE)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH1)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_HASH2)), cursor.getString(cursor.getColumnIndex(LOGIN_KEY_PHOTO)));
        }
        cursor.close();
        return login;
    }


    public UserCurrentAddress getCurrentAddress() {
        UserCurrentAddress login = null;
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESS, null);
        if (cursor.moveToNext()) {
            login = new UserCurrentAddress(cursor.getString(cursor.getColumnIndex(ADDRESS_CURRENT_MOBILE)),cursor.getString(cursor.getColumnIndex(ADDRESS_CURRENT_ADDRESS)),cursor.getString(cursor.getColumnIndex(ADDRESS_CURRENT_LATITUDE)), cursor.getString(cursor.getColumnIndex(ADDRESS_CURRENT_LONGITUDE)));
        }
        cursor.close();
        return login;
    }
    public boolean getAddress(String mobile){
        boolean value;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cd = db.rawQuery("SELECT * FROM " + TABLE_ADDRESS+" WHERE mobile ="+mobile, null);
        if(cd.moveToFirst())
        {
            value = true;
        }
        else
        {
            value = false;
        }
        return  value;
    }

    public void updateAddress(UserCurrentAddress data) {
        SQLiteDatabase db = getDB();
        ContentValues cv = new ContentValues();
        cv.put(ADDRESS_CURRENT_ADDRESS, data.getAddress());
        cv.put(ADDRESS_CURRENT_LONGITUDE, data.getLongitude());
        cv.put(ADDRESS_CURRENT_LATITUDE, data.getLatitude());
        db.update(TABLE_ADDRESS, cv,  "mobile = ?", new String[]{data.getMobile()});
    }
    //UserCurrentAddress

    public void dropDB() {
        SQLiteDatabase db = getDB();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
    }

    private SQLiteDatabase getDB() {
        return this.getWritableDatabase();
    }
}
