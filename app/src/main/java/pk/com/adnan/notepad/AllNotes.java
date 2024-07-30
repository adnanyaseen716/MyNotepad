package pk.com.adnan.notepad;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllNotes extends AppCompatActivity {
//----------------------------------------DRAWER ----------------------------
     DrawerLayout drawerLayout;
      MaterialToolbar btnDrawToggle;
       NavigationView navigationView;

    //------------------------------------------------------------------------
      RecyclerView recyclerView;
     NoteAdapter noteAdapter;
    FloatingActionButton btnAdd;
    List<Note> noteList;
    DB db = DB.getInstance(AllNotes.this);
    DBTrash dbTrash = DBTrash.getInstance(AllNotes.this);
DBFavourites dbFavourites = DBFavourites.getInstance(AllNotes.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allnotes);

        recyclerView = findViewById(R.id.rvRecyclerView);
        btnAdd = findViewById(R.id.btnAdd);


        List<Note> noteList = db.fetchNote();

        noteAdapter = new NoteAdapter(noteList , AllNotes.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllNotes.this));
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);


        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(AllNotes.this, UpdateNote.class);

                    String titleIntent = noteList.get(position).getNoteTitle();
                    String descriptionIntent = noteList.get(position).getNoteDescription();

                    intent.putExtra("title", titleIntent);
                    intent.putExtra("description", descriptionIntent);

                    startActivity(intent);
                }
        });


        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AllNotes.this , AddNotes.class);
            startActivity(intent);
        });




//-------------------------------- DrawerLayout ------------------------------------------------------------



        drawerLayout = findViewById(R.id.drawerLayout);
        btnDrawToggle = findViewById(R.id.drawerr);
        navigationView = findViewById(R.id.navigationView);

        btnDrawToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navAllNotes){

                } else if (itemId == R.id.navFavourite){
                    Intent intent = new Intent(AllNotes.this , Favourites.class);
                    startActivity(intent);
                }else if (itemId == R.id.navRecyclebin){
                    Intent intent = new Intent(AllNotes.this , Trash.class);
                    startActivity(intent);

                }else if (itemId == R.id.navFeedback){

                    Dialog dialog = new Dialog(AllNotes.this);
                    dialog.setContentView(R.layout.dlg_feedback);

                    TextView tvNoFeedback = dialog.findViewById(R.id.tvNoFeedback);
                    TextView tvYesFeedback = dialog.findViewById(R.id.tvYesFeedback);

                    tvNoFeedback.setOnClickListener(v -> {
                        dialog.dismiss();
                        Dialog dialogNoFeedback = new Dialog(AllNotes.this);
                        dialogNoFeedback.setContentView(R.layout.dlg_no_feedback);
                        Button btnSubmit = dialogNoFeedback.findViewById(R.id.btnSubmitNo);
                        btnSubmit.setOnClickListener(v1 -> {
                            dialogNoFeedback.dismiss();
                            Toast.makeText(AllNotes.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                        });
                        dialogNoFeedback.show();
                    });

                    tvYesFeedback.setOnClickListener(v -> {
                        dialog.dismiss();
                        Dialog dialogYesFeedback = new Dialog(AllNotes.this);
                        dialogYesFeedback.setContentView(R.layout.dlg_yes_feeback);

                        Button btnSubmit = dialogYesFeedback.findViewById(R.id.btnSubmitYes);
                        btnSubmit.setOnClickListener(v12 -> {
                            dialogYesFeedback.dismiss();
                            Toast.makeText(AllNotes.this, "Thank you for rating us!", Toast.LENGTH_SHORT).show();
                        });


                        RatingBar ratingBar = dialogYesFeedback.findViewById(R.id.ratingBar);
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                TextView tvEnteredRating = dialogYesFeedback.findViewById(R.id.tvEnteredRating);
                                tvEnteredRating.setText( "You rated us "+rating+" stars");
                            }
                        });


                        dialogYesFeedback.show();


                    });

                    dialog.show();


                } else if (itemId == R.id.navShare) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Here is My Notes App! Please try it! \nhttps://www.google.com/profile.php?id=pk.adnan.notepad");
                     shareIntent.setType("text/plain");
                     shareIntent = Intent.createChooser(shareIntent,"Share via : ");
                     startActivity(shareIntent);

                } else if (itemId == R.id.navExit){
//                    AlertDialog alertDialog =
                            new AlertDialog.Builder(AllNotes.this)
                            .setMessage("Do you want to close this app?")
                            .setCancelable(false)
                            .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                }

                drawerLayout.close();


                return false;
            }
        });




//----------------------------MenuItem ClickListener---------------------------------------------------

        btnDrawToggle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.listView){

                    noteAdapter = new NoteAdapter(noteList  , AllNotes.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllNotes.this));
                    recyclerView.setAdapter(noteAdapter);
                    recyclerView.setHasFixedSize(true);

                    noteAdapter.setOnItemClickListener(position -> {
                        Intent intent = new Intent(AllNotes.this, UpdateNote.class);

                        String titleIntent = noteList.get(position).getNoteTitle();
                        String descriptionIntent = noteList.get(position).getNoteDescription();


                        intent.putExtra("title", titleIntent);
                        intent.putExtra("description", descriptionIntent);

                        startActivity(intent);
                    });

                    noteAdapter.setOnItemLongClickListener(new NoteAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int position) {

                            String title = noteList.get(position).getNoteTitle();
                            String description = noteList.get(position).getNoteDescription();

                            final Dialog dialog = new Dialog(AllNotes.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.bottom_sheet_dlg_layout);

// ----------------------------------DELETE-----------------------------------------------------------------


                            Button btnDeleteDlg = dialog.findViewById(R.id.btnDeleteDlg);

                            btnDeleteDlg.setOnClickListener(v -> {

                                dialog.dismiss();

                                Note note = new Note(title , description);

                                //---------------------TRASH------------------------------------------------
                                TrashModel trashModel = new TrashModel(title , description);
                                dbTrash.insertTrashNote(trashModel);

                                //--------------------------------------------------------------------------------
                                if(db.deleteNote(note)){
                                    Toast.makeText(AllNotes.this, "Note added to RecycleBin!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AllNotes.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                                }
                            });

// ---------------------------------- FAVOURITES -----------------------------------------------------------------

                            Button btnFavDlg = dialog.findViewById(R.id.btnFavDlg);

                            btnFavDlg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();
                                    FavouritesModel favouritesModel = new FavouritesModel(title , description);

                                    if (dbFavourites.insertFavNote(favouritesModel,AllNotes.this)){
                                        Toast.makeText(AllNotes.this, "Note added to Favourites!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


// -----------------------------------------------------------------------------------------------------

                            TextView tvCrossAllNotes = dialog.findViewById(R.id.tvCrossAllNotes);
                            tvCrossAllNotes.setOnClickListener(v -> {
                                dialog.dismiss();
                            });



                            dialog.show();

                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.BOTTOM);

                        }
                    });

                }else if (itemId == R.id.gridView){

                    NoteAdapterGrid noteAdapterGrid = new NoteAdapterGrid(noteList);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(noteAdapterGrid);
                    recyclerView.setHasFixedSize(true);

                } else if (itemId == R.id.search_view) {

                    SearchView searchView = (SearchView) item.getActionView();

                    searchView.setQueryHint("Search Notes...");

                    assert searchView != null;
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            noteAdapter.getFilter().filter(newText);

                            return true;
                        }

                    });

                }

                return true;
            }

        });



        //--------------------------- BOTTOM SHEET DIALOG ------------------------------------------


        noteAdapter.setOnItemLongClickListener(new NoteAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {

                String title = noteList.get(position).getNoteTitle();
                String description = noteList.get(position).getNoteDescription();

                final Dialog dialog = new Dialog(AllNotes.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_dlg_layout);

// ----------------------------------DELETE-----------------------------------------------------------------


                Button btnDeleteDlg = dialog.findViewById(R.id.btnDeleteDlg);

                btnDeleteDlg.setOnClickListener(v -> {

                    dialog.dismiss();

                    Note note = new Note(title , description);

                    //---------------------TRASH------------------------------------------------
                    TrashModel trashModel = new TrashModel(title , description);
                    dbTrash.insertTrashNote(trashModel);

                    //--------------------------------------------------------------------------------
                    if(db.deleteNote(note)){
                        Toast.makeText(AllNotes.this, "Note added to RecycleBin!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AllNotes.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

// ---------------------------------- FAVOURITES -----------------------------------------------------------------

                Button btnFavDlg = dialog.findViewById(R.id.btnFavDlg);

                btnFavDlg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        FavouritesModel favouritesModel = new FavouritesModel(title , description);

                        if (dbFavourites.insertFavNote(favouritesModel,AllNotes.this)){
                            Toast.makeText(AllNotes.this, "Note added to Favourites!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


// -----------------------------------------------------------------------------------------------------

                TextView tvCrossAllNotes = dialog.findViewById(R.id.tvCrossAllNotes);
                tvCrossAllNotes.setOnClickListener(v -> {
                    dialog.dismiss();
                });



                dialog.show();

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });






    }





    //-----------------------------------------------------------ON RESUME ----------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_allnotes);

        recyclerView = findViewById(R.id.rvRecyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        noteList = db.fetchNote();

        noteAdapter = new NoteAdapter(noteList , AllNotes.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllNotes.this));
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);


        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AllNotes.this, UpdateNote.class);

                String titleIntent = noteList.get(position).getNoteTitle();
                String descriptionIntent = noteList.get(position).getNoteDescription();

                intent.putExtra("title", titleIntent);
                intent.putExtra("description", descriptionIntent);

                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AllNotes.this , AddNotes.class);
            startActivity(intent);
        });




//-------------------------------- DrawerLayout ------------------------------------------------------------



        drawerLayout = findViewById(R.id.drawerLayout);
        btnDrawToggle = findViewById(R.id.drawerr);
        navigationView = findViewById(R.id.navigationView);

        btnDrawToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navAllNotes){

                } else if (itemId == R.id.navFavourite){
                    Intent intent = new Intent(AllNotes.this , Favourites.class);
                    startActivity(intent);
                }else if (itemId == R.id.navRecyclebin){
                    Intent intent = new Intent(AllNotes.this , Trash.class);
                    startActivity(intent);

                }else if (itemId == R.id.navFeedback){

                    Dialog dialog = new Dialog(AllNotes.this);
                    dialog.setContentView(R.layout.dlg_feedback);

                    TextView tvNoFeedback = dialog.findViewById(R.id.tvNoFeedback);
                    TextView tvYesFeedback = dialog.findViewById(R.id.tvYesFeedback);

                    tvNoFeedback.setOnClickListener(v -> {
                        dialog.dismiss();
                        Dialog dialogNoFeedback = new Dialog(AllNotes.this);
                        dialogNoFeedback.setContentView(R.layout.dlg_no_feedback);
                        Button btnSubmit = dialogNoFeedback.findViewById(R.id.btnSubmitNo);
                        btnSubmit.setOnClickListener(v1 -> {
                            dialogNoFeedback.dismiss();
                            Toast.makeText(AllNotes.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                        });
                        dialogNoFeedback.show();
                    });

                    tvYesFeedback.setOnClickListener(v -> {
                        dialog.dismiss();
                        Dialog dialogYesFeedback = new Dialog(AllNotes.this);
                        dialogYesFeedback.setContentView(R.layout.dlg_yes_feeback);

                        Button btnSubmit = dialogYesFeedback.findViewById(R.id.btnSubmitYes);
                        btnSubmit.setOnClickListener(v12 -> {
                            dialogYesFeedback.dismiss();
                            Toast.makeText(AllNotes.this, "Thank you for rating us!", Toast.LENGTH_SHORT).show();
                        });


                        RatingBar ratingBar = dialogYesFeedback.findViewById(R.id.ratingBar);
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                TextView tvEnteredRating = dialogYesFeedback.findViewById(R.id.tvEnteredRating);
                                tvEnteredRating.setText( "You rated us "+rating+" stars");
                            }
                        });


                        dialogYesFeedback.show();


                    });

                    dialog.show();


                } else if (itemId == R.id.navShare) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Here is My Notes App! Please try it! \nhttps://www.google.com/profile.php?id=pk.adnan.notepad");
                    shareIntent.setType("text/plain");
                    shareIntent = Intent.createChooser(shareIntent,"Share via : ");
                    startActivity(shareIntent);

                } else if (itemId == R.id.navExit){
//                    AlertDialog alertDialog =
                    new AlertDialog.Builder(AllNotes.this)
                            .setMessage("Do you want to close this app?")
                            .setCancelable(false)
                            .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                }

                drawerLayout.close();


                return false;
            }
        });




//----------------------------MenuItem ClickListener---------------------------------------------------

        btnDrawToggle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.listView){

                    noteAdapter = new NoteAdapter(noteList  , AllNotes.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllNotes.this));
                    recyclerView.setAdapter(noteAdapter);
                    recyclerView.setHasFixedSize(true);

                    noteAdapter.setOnItemClickListener(position -> {
                        Intent intent = new Intent(AllNotes.this, UpdateNote.class);

                        String titleIntent = noteList.get(position).getNoteTitle();
                        String descriptionIntent = noteList.get(position).getNoteDescription();


                        intent.putExtra("title", titleIntent);
                        intent.putExtra("description", descriptionIntent);

                        startActivity(intent);
                    });

                    noteAdapter.setOnItemLongClickListener(new NoteAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int position) {

                            String title = noteList.get(position).getNoteTitle();
                            String description = noteList.get(position).getNoteDescription();

                            final Dialog dialog = new Dialog(AllNotes.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.bottom_sheet_dlg_layout);

// ----------------------------------DELETE-----------------------------------------------------------------


                            Button btnDeleteDlg = dialog.findViewById(R.id.btnDeleteDlg);

                            btnDeleteDlg.setOnClickListener(v -> {

                                dialog.dismiss();

                                Note note = new Note(title , description);

                                //---------------------TRASH------------------------------------------------
                                TrashModel trashModel = new TrashModel(title , description);
                                dbTrash.insertTrashNote(trashModel);

                                //--------------------------------------------------------------------------------
                                if(db.deleteNote(note)){
                                    Toast.makeText(AllNotes.this, "Note added to RecycleBin!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AllNotes.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                                }
                            });

// ---------------------------------- FAVOURITES -----------------------------------------------------------------

                            Button btnFavDlg = dialog.findViewById(R.id.btnFavDlg);

                            btnFavDlg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();
                                    FavouritesModel favouritesModel = new FavouritesModel(title , description);

                                    if (dbFavourites.insertFavNote(favouritesModel,AllNotes.this)){
                                        Toast.makeText(AllNotes.this, "Note added to Favourites", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


// -----------------------------------------------------------------------------------------------------

                            TextView tvCrossAllNotes = dialog.findViewById(R.id.tvCrossAllNotes);
                            tvCrossAllNotes.setOnClickListener(v -> {
                                dialog.dismiss();
                            });



                            dialog.show();

                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.BOTTOM);

                        }
                    });

                }else if (itemId == R.id.gridView){

                    NoteAdapterGrid noteAdapterGrid = new NoteAdapterGrid(noteList);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(noteAdapterGrid);
                    recyclerView.setHasFixedSize(true);

                } else if (itemId == R.id.search_view) {

                    SearchView searchView = (SearchView) item.getActionView();

                    searchView.setQueryHint("Search Notes...");

                    assert searchView != null;
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            noteAdapter.getFilter().filter(newText);

                            return true;
                        }

                    });

                }

                return true;
            }

        });



        //--------------------------- BOTTOM SHEET DIALOG ------------------------------------------


        noteAdapter.setOnItemLongClickListener(new NoteAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {

                String title = noteList.get(position).getNoteTitle();
                String description = noteList.get(position).getNoteDescription();

                final Dialog dialog = new Dialog(AllNotes.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_dlg_layout);

// ----------------------------------DELETE-----------------------------------------------------------------


                Button btnDeleteDlg = dialog.findViewById(R.id.btnDeleteDlg);

                btnDeleteDlg.setOnClickListener(v -> {

                    dialog.dismiss();


                    //---------------------TRASH------------------------------------------------
                    TrashModel trashModel = new TrashModel(title , description);
                    dbTrash.insertTrashNote(trashModel);

                    Note note = new Note(title , description);

                    //--------------------------------------------------------------------------------
                    if(db.deleteNote(note)){
                        Toast.makeText(AllNotes.this, "Note added to RecycleBin!", Toast.LENGTH_SHORT).show();

                        noteList.remove(position);
                        noteAdapter.notifyItemRemoved(position);

                    }else {
                        Toast.makeText(AllNotes.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

// ---------------------------------- FAVOURITES -----------------------------------------------------------------

                Button btnFavDlg = dialog.findViewById(R.id.btnFavDlg);

                btnFavDlg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        FavouritesModel favouritesModel = new FavouritesModel(title , description);

                        if (dbFavourites.insertFavNote(favouritesModel,AllNotes.this)){
                            Toast.makeText(AllNotes.this, "Note added to Favourites!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


// -----------------------------------------------------------------------------------------------------

                TextView tvCrossAllNotes = dialog.findViewById(R.id.tvCrossAllNotes);
                tvCrossAllNotes.setOnClickListener(v -> {
                    dialog.dismiss();
                });



                dialog.show();

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });












    }

}