package jp.blogspot.jusoncode.adverbialtwo.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



class DataBase extends SQLiteOpenHelper {

    DataBase(Context context) {
        super(context, "students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table student(_id integer primary key autoincrement," +
                "name not null," + "comment text," + "grammar text," + "vocabulary text,"+"listening text,"+"fluency text,"+"communicative text)";
        db.execSQL(sql);
        //////Create the second table for the lessons....
        String sqlLesson = "create table lesson(_id integer primary key autoincrement," +
                "name not null," + "pupils text," + "taken text)";
        db.execSQL(sqlLesson);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
