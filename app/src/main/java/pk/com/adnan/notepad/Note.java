package pk.com.adnan.notepad;

import android.graphics.Bitmap;

import java.util.Objects;

public class Note {
    private String noteTitle;
   private String noteDescription;
    public static final String TABLE_NAME = "Note";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";
//    public static final String COL_IMAGE = "Image";
//    public static final String COL_TIME = "Time";

//public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY , %s TEXT, %s REAL )", TABLE_NAME,COL_ROLL_NO,COL_NAME,COL_FEE);

    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( %s TEXT PRIMARY KEY , %s TEXT )", TABLE_NAME,COL_TITLE,COL_DESCRIPTION);

    public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_NOTES = " SELECT * FROM "+TABLE_NAME;
    public Note() {
    }



    public Note( String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(noteTitle, note.noteTitle) && Objects.equals(noteDescription, note.noteDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteTitle, noteDescription);
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                '}';
    }
}
