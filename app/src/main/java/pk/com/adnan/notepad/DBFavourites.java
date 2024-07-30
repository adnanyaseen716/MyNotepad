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

public class DBFavourites extends SQLiteOpenHelper {
    private static DBFavourites instance;
    public static final String DB_FAV_NAME = "FAVOURITES";
    public static final int DB_FAV_VERSION = 1;

    private DBFavourites(Context context) {
        super(context, DB_FAV_NAME, null, DB_FAV_VERSION);

    }

    public static DBFavourites getInstance(Context context) {
        if (instance == null) {

            instance = new DBFavourites(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavouritesModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(FavouritesModel.DROP_TABLE);
            db.execSQL(FavouritesModel.CREATE_TABLE);
        }
    }

    public boolean insertFavNote(FavouritesModel favourites , Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavouritesModel.COL_TITLE,favourites.getFavTitle());
        contentValues.put(FavouritesModel.COL_DESCRIPTION, favourites.getFavDescription());
        long rowId;
        try {
            rowId = db.insert(FavouritesModel.TABLE_NAME,null,contentValues);

        }catch (Exception ex){
            return false;

        }

        if (rowId == -1){
            Toast.makeText(context, "Note already present in favourites!", Toast.LENGTH_SHORT).show();
        }

        return rowId!= -1;
    }
//---------------------------------

    public boolean deleteFavNote (FavouritesModel favourites){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;

        try{
            rowId = db.delete(FavouritesModel.TABLE_NAME  ,FavouritesModel.COL_TITLE+" = ?",new String[]{String.valueOf((favourites.getFavTitle()))
            });

        }catch (Exception ex){
            return false;
        }

        return rowId != -1;
    }



    public List<FavouritesModel> fetchFavNote(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(FavouritesModel.SELECT_ALL_NOTES,null);
        List<FavouritesModel> favourites = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do{
                FavouritesModel favourite = new FavouritesModel();

                int index = cursor.getColumnIndex(TrashModel.COL_TITLE);
                favourite.setFavTitle(cursor.getString(index));

                index = cursor.getColumnIndex(TrashModel.COL_DESCRIPTION);
                favourite.setFavDescription(cursor.getString(index));

                favourites.add(favourite);

            }while (cursor.moveToNext());


        }


        return favourites;


    }
}
