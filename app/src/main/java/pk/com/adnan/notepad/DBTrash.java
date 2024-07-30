package pk.com.adnan.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBTrash extends SQLiteOpenHelper {
    private static DBTrash instance;
    public static final String DB_TRASH_NAME = "TRASH";
    public static final int DB_TRASH_VERSION = 1;

    private DBTrash(Context context) {
        super(context, DB_TRASH_NAME, null, DB_TRASH_VERSION);

    }

    public static DBTrash getInstance(Context context) {
        if (instance == null) {

            instance = new DBTrash(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrashModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(TrashModel.DROP_TABLE);
            db.execSQL(TrashModel.CREATE_TABLE);
        }
    }

    public boolean insertTrashNote(TrashModel trash){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrashModel.COL_TITLE,trash.getTrashTitle());
        contentValues.put(TrashModel.COL_DESCRIPTION, trash.getTrashDescription());
        long rowId;
        try {
            rowId = db.insert(TrashModel.TABLE_NAME,null,contentValues);

        }catch (Exception ex){
            return false;

        }

        return rowId!= -1;
    }

    public boolean deleteTrashNote (TrashModel trash){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;

        try{
            rowId = db.delete(TrashModel.TABLE_NAME  ,TrashModel.COL_TITLE+" = ?",new String[]{String.valueOf((trash.getTrashTitle()))
            });

        }catch (Exception ex){
            return false;
        }

        return rowId != -1;
    }
//
//
//
//
//    public boolean updateTrashNote(Note note , Context context){
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(Note.COL_TITLE,note.getNoteTitle());
//        contentValues.put(Note.COL_DESCRIPTION, note.getNoteDescription());
//        long rowId;
//        try {
////            rowId = db.update(Student.TABLE_NAME,contentValues,Student.COL_ROLL_NO +"= ?",new  String[student.getRollNo()]);
//            rowId = db.update(Note.TABLE_NAME,contentValues,Note.COL_TITLE +"= ?",new  String[]{String.valueOf((note.getNoteTitle()))
//            });
//
//        }catch (Exception ex){
//            return  false;
//        }
//
//
//        if (rowId == -1){
//            Toast.makeText(context, "Record already exists!", Toast.LENGTH_SHORT).show();
//        }
//        return rowId!= -1;
//    }

    public List<TrashModel> fetchTrashNote(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TrashModel.SELECT_ALL_NOTES,null);
        List<TrashModel> trashes = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do{
                TrashModel trash = new TrashModel();

                int index = cursor.getColumnIndex(TrashModel.COL_TITLE);
                trash.setTrashTitle(cursor.getString(index));

                index = cursor.getColumnIndex(TrashModel.COL_DESCRIPTION);
                trash.setTrashDescription(cursor.getString(index));

                trashes.add(trash);

            }while (cursor.moveToNext());


        }


        return trashes;


    }
}
