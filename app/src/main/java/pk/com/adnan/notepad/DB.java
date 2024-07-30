package pk.com.adnan.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static DB instance;
    public static final String DB_NAME = "NOTES";
    public static final int DB_VERSION = 1;

    private DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public static DB getInstance(Context context) {
        if (instance == null) {

            instance = new DB(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(Note.DROP_TABLE);
            db.execSQL(Note.CREATE_TABLE);
        }
    }

    public boolean insertNote(Note note ,Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COL_TITLE,note.getNoteTitle());
        contentValues.put(Note.COL_DESCRIPTION, note.getNoteDescription());
        long rowId;
        try {
            rowId = db.insert(Note.TABLE_NAME,null,contentValues);

        }catch (Exception ex){
            return false;

        }

        if (rowId == -1){
            Toast.makeText(context, "Note with this title is already present!", Toast.LENGTH_SHORT).show();
        }
        return rowId!= -1;
    }


    public boolean deleteNote (Note note){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;

        try{

//            rowId = db.delete(Student.TABLE_NAME  ,Student.COL_ROLL_NO+" = ?",new String[student.getRollNo()]);

            rowId = db.delete(Note.TABLE_NAME  ,Note.COL_TITLE+" = ?",new String[]{String.valueOf((note.getNoteTitle()))
            });

        }catch (Exception ex){
            return false;
        }

        return rowId != -1;
    }




    public boolean updateNote(Note note , Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Note.COL_TITLE,note.getNoteTitle());
        contentValues.put(Note.COL_DESCRIPTION, note.getNoteDescription());
        long rowId;
        try {
//            rowId = db.update(Student.TABLE_NAME,contentValues,Student.COL_ROLL_NO +"= ?",new  String[student.getRollNo()]);
            rowId = db.update(Note.TABLE_NAME,contentValues,Note.COL_TITLE +"= ?",new  String[]{String.valueOf((note.getNoteTitle()))
            });

        }catch (Exception ex){
            return  false;
        }


        if (rowId == -1){
            Toast.makeText(context, "Note with this title is already present!", Toast.LENGTH_SHORT).show();
        }
        return rowId!= -1;
    }

    public List<Note> fetchNote(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Note.SELECT_ALL_NOTES,null);
        List<Note> notes = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do{
                Note note = new Note();
                int index = cursor.getColumnIndex(Note.COL_TITLE);
                note.setNoteTitle(cursor.getString(index));
                index = cursor.getColumnIndex(Note.COL_DESCRIPTION);
                note.setNoteDescription(cursor.getString(index));

                notes.add(note);

            }while (cursor.moveToNext());
        }
        return notes;
    }
}

