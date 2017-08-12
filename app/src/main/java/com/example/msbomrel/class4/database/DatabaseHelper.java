package com.example.msbomrel.class4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.msbomrel.class4.data.Student;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by msbomrel on 7/8/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "class4";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "student";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String ROLL_NO = "roll";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NAME + " TEXT," + ADDRESS + " TEXT," +
                ROLL_NO + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertStudent(Student student){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, student.getUsername());
        contentValues.put(ADDRESS, student.getAddress());
        contentValues.put(ROLL_NO, student.getRoll());
        long status = sqLiteDatabase.insert(TABLE_NAME, ID, contentValues);
        sqLiteDatabase.close();
        return  (status != -1);
    }

    public List<Student> getStudentList(){
        List<Student> list = new LinkedList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{ID,NAME,ADDRESS, ROLL_NO}, null, null,null,null,null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Student student = new Student(cursor.getInt(cursor.getColumnIndex(ID)),cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(ADDRESS)), cursor.getInt(cursor.getColumnIndex(ROLL_NO)));
                list.add(student);
            }while (cursor.moveToNext());

        }
        cursor.close();
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
        return  list;
    }
    public boolean deleteStudent(Student student){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long status = sqLiteDatabase.delete(TABLE_NAME, ID + " =?", new String[]{ String.valueOf(student.getId())});
        sqLiteDatabase.close();
        return  (status != -1);
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, student.getUsername());
        values.put(ADDRESS, student.getAddress());
        values.put(ROLL_NO, student.getRoll());


        // updating row
        long status = sqLiteDatabase.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(student.getId()) });

        sqLiteDatabase.close();
        return  (status != -1);
    }
}
