package pk.com.adnan.notepad;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favourites extends AppCompatActivity {
    private RecyclerView rvFavourites;
    private FavouritesAdapter favouritesAdapter;
    private List<FavouritesModel> favouritesList;
    DBFavourites dbFavourites = DBFavourites.getInstance(Favourites.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourites);

        rvFavourites = findViewById(R.id.rvFavourites);

        favouritesList = dbFavourites.fetchFavNote();
        favouritesAdapter = new FavouritesAdapter(favouritesList);
        rvFavourites.setAdapter(favouritesAdapter);

        rvFavourites.setLayoutManager(new LinearLayoutManager(this));
        rvFavourites.setHasFixedSize(true);


        favouritesAdapter.setOnItemLongClickListener(position -> {

            String title = favouritesList.get(position).getFavTitle();
            String description = favouritesList.get(position).getFavDescription();

            final Dialog dialog = new Dialog(Favourites.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_fav_dlg);

            TextView tvAskDeleteFav = dialog.findViewById(R.id.tvAskDeleteFav);
            tvAskDeleteFav.setText("Do you want to remove  "+"\""+favouritesList.get(position).getFavTitle()+"\""+"  from Favourites ?");

            Button btnDeleteFav = dialog.findViewById(R.id.btnDeleteFav);

            btnDeleteFav.setOnClickListener(v -> {

                dialog.dismiss();
                FavouritesModel favouritesModel = new FavouritesModel(title , description);
                if (dbFavourites.deleteFavNote(favouritesModel)){
                    Toast.makeText(Favourites.this, "Removed from Favourites!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Favourites.this, "Note not removed from Favourites!", Toast.LENGTH_SHORT).show();
                }

            });


           TextView tvCrossFav = dialog.findViewById(R.id.tvCrossFav);
            tvCrossFav.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);

        });

    }


    @Override
    protected void onResume() {
        super.onResume();




        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourites);

        rvFavourites = findViewById(R.id.rvFavourites);

        favouritesList = dbFavourites.fetchFavNote();
        favouritesAdapter = new FavouritesAdapter(favouritesList);
        rvFavourites.setAdapter(favouritesAdapter);

        rvFavourites.setLayoutManager(new LinearLayoutManager(this));
        rvFavourites.setHasFixedSize(true);


        favouritesAdapter.setOnItemLongClickListener(position -> {

            String title = favouritesList.get(position).getFavTitle();
            String description = favouritesList.get(position).getFavDescription();

            final Dialog dialog = new Dialog(Favourites.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_fav_dlg);

            TextView tvAskDeleteFav = dialog.findViewById(R.id.tvAskDeleteFav);
            tvAskDeleteFav.setText("Do you want to remove  "+"\""+favouritesList.get(position).getFavTitle()+"\""+"  from Favourites ?");

            Button btnDeleteFav = dialog.findViewById(R.id.btnDeleteFav);

            btnDeleteFav.setOnClickListener(v -> {

                dialog.dismiss();
                FavouritesModel favouritesModel = new FavouritesModel(title , description);
                if (dbFavourites.deleteFavNote(favouritesModel)){
                    Toast.makeText(Favourites.this, "Removed from Favourites!", Toast.LENGTH_SHORT).show();

                    favouritesList.remove(position);
                    favouritesAdapter.notifyItemRemoved(position);

                }else {
                    Toast.makeText(Favourites.this, "Note not removed from Favourites!", Toast.LENGTH_SHORT).show();
                }

            });


            TextView tvCrossFav = dialog.findViewById(R.id.tvCrossFav);
            tvCrossFav.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);

        });




    }
}