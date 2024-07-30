package pk.com.adnan.notepad;

import java.util.Objects;

public class FavouritesModel {

    private String favTitle;
    private String favDescription;

    //----------------------------------------------DB-----------------------------------------------

    public static final String TABLE_NAME = "Favourites";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";
    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( %s TEXT PRIMARY KEY , %s TEXT )", TABLE_NAME,COL_TITLE,COL_DESCRIPTION);

    public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_NOTES = " SELECT * FROM "+TABLE_NAME;

    //-------------------------------------------------------------------------------------------


    public FavouritesModel() {
    }

    public FavouritesModel( String favTitle, String favDescription) {
        this.favTitle = favTitle;
        this.favDescription = favDescription;
    }





    public String getFavTitle() {
        return favTitle;
    }

    public void setFavTitle(String favTitle) {
        this.favTitle = favTitle;
    }

    public String getFavDescription() {
        return favDescription;
    }

    public void setFavDescription(String favDescription) {
        this.favDescription = favDescription;
    }

    @Override
    public String toString() {
        return "FavouritesModel{" +
                "favTitle='" + favTitle + '\'' +
                ", favDescription='" + favDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavouritesModel)) return false;
        FavouritesModel that = (FavouritesModel) o;
        return Objects.equals(favTitle, that.favTitle) && Objects.equals(favDescription, that.favDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favTitle, favDescription);
    }
}
