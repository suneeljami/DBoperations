package com.example.suneel.dboperations.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by suneel on 2/14/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="skdatabase.db";
    Context context;
    private static String dbPath = "/data/data/com.example.suneel.dboperations.database/databases/";
    private static DBHelper dbInstance=null;
    private SQLiteDatabase db;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
        this.context=context;
    }
    public static DBHelper getInstance(Context ctx) {
        if (dbInstance == null) {
            dbInstance = new DBHelper(ctx.getApplicationContext());
            try {
                dbInstance.createDataBase();
                dbInstance.openDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dbInstance;
    }
    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    public void openDataBase() throws SQLException, IOException {
        String fullDbPath = dbPath + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(fullDbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDataBase() {
        File dbFile = new File(dbPath + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        try {

            InputStream input = context.getAssets().open(DATABASE_NAME);
            String outPutFileName = dbPath + DATABASE_NAME;
            OutputStream output = new FileOutputStream(outPutFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            Log.v("error", e.toString());
        }
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
