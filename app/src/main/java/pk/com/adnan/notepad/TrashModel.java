package pk.com.adnan.notepad;

import java.util.Objects;

public class TrashModel {

    private String trashTitle;
    private String trashDescription;
//----------------------------------------------DB-----------------------------------------------

    public static final String TABLE_NAME = "Trash";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";
    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( %s TEXT PRIMARY KEY , %s TEXT )", TABLE_NAME,COL_TITLE,COL_DESCRIPTION);

    public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_NOTES = " SELECT * FROM "+TABLE_NAME;


    //--------------
    public TrashModel() {
    }

    public TrashModel(String trashTitle, String trashDescription) {
        this.trashTitle = trashTitle;
        this.trashDescription = trashDescription;
    }


    public String getTrashTitle() {
        return trashTitle;
    }

    public void setTrashTitle(String trashTitle) {
        this.trashTitle = trashTitle;
    }

    public String getTrashDescription() {
        return trashDescription;
    }

    public void setTrashDescription(String trashDescription) {
        this.trashDescription = trashDescription;
    }

    @Override
    public String toString() {
        return "RecyclebinModel{" +
                "trashTitle='" + trashTitle + '\'' +
                ", trashDescription='" + trashDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrashModel)) return false;
        TrashModel that = (TrashModel) o;
        return Objects.equals(trashTitle, that.trashTitle) && Objects.equals(trashDescription, that.trashDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trashTitle, trashDescription);
    }
}
