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
import java.util.List;

public class Trash extends AppCompatActivity {
    DBTrash dbTrash = DBTrash.getInstance(Trash.this);
    private RecyclerView rvRecyclebin;
    private TrashAdapter trashAdapter;


List<TrashModel> trashList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trash);

        rvRecyclebin = findViewById(R.id.rvRecyclebin);


        trashList = dbTrash.fetchTrashNote();
        trashAdapter = new TrashAdapter(trashList);
        rvRecyclebin.setAdapter(trashAdapter);

        rvRecyclebin.setLayoutManager(new LinearLayoutManager(this));
        rvRecyclebin.setHasFixedSize(true);













        //--------------------------- BOTTOM SHEET DIALOG ------------------------------------------


        trashAdapter.setOnItemLongClickListener(position -> {

            String title = trashList.get(position).getTrashTitle();
            String description = trashList.get(position).getTrashDescription();

            final Dialog dialog = new Dialog(Trash.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_trash_dlg);

            TextView tvAskDeleteTrash = dialog.findViewById(R.id.tvAskDeleteTrash);
            tvAskDeleteTrash.setText("Do you want to permanently delete "+"\""+trashList.get(position).getTrashTitle()+"\""+" ?");

            Button btnDeleteTrash = dialog.findViewById(R.id.btnDeleteTrash);

            btnDeleteTrash.setOnClickListener(v -> {

                dialog.dismiss();
                TrashModel trashModel = new TrashModel(title , description);
                if (dbTrash.deleteTrashNote(trashModel)){
                    Toast.makeText(Trash.this, "Note deleted permanently!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Trash.this, "Note not deleted", Toast.LENGTH_SHORT).show();
                }

            });


            TextView tvCrossTrash = dialog.findViewById(R.id.tvCrossTrash);
            tvCrossTrash.setOnClickListener(v -> {
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




        setContentView(R.layout.activity_trash);

        rvRecyclebin = findViewById(R.id.rvRecyclebin);


        trashList = dbTrash.fetchTrashNote();
        trashAdapter = new TrashAdapter(trashList);
        rvRecyclebin.setAdapter(trashAdapter);

        rvRecyclebin.setLayoutManager(new LinearLayoutManager(this));
        rvRecyclebin.setHasFixedSize(true);













        //--------------------------- BOTTOM SHEET DIALOG ------------------------------------------


        trashAdapter.setOnItemLongClickListener(position -> {

            String title = trashList.get(position).getTrashTitle();
            String description = trashList.get(position).getTrashDescription();
            TrashModel trashModel = new TrashModel(title , description);

            final Dialog dialog = new Dialog(Trash.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_trash_dlg);

            TextView tvAskDeleteTrash = dialog.findViewById(R.id.tvAskDeleteTrash);
            tvAskDeleteTrash.setText("Do you want to permanently delete "+"\""+trashList.get(position).getTrashTitle()+"\""+" ?");

            Button btnDeleteTrash = dialog.findViewById(R.id.btnDeleteTrash);

            btnDeleteTrash.setOnClickListener(v -> {

                dialog.dismiss();
                if (dbTrash.deleteTrashNote(trashModel)){
                    Toast.makeText(Trash.this, "Note deleted permanently!", Toast.LENGTH_SHORT).show();

                    trashList.remove(position);
                    trashAdapter.notifyItemRemoved(position);

                }else {
                    Toast.makeText(Trash.this, "Note not deleted", Toast.LENGTH_SHORT).show();
                }

            });


            Button btnRestore = dialog.findViewById(R.id.btnRestore);
            btnRestore.setOnClickListener(v -> {
                dialog.dismiss();

                DB db = DB.getInstance(Trash.this);
                Note note = new Note(title,description);
                db.insertNote(note,Trash.this);

                if (dbTrash.deleteTrashNote(trashModel)){
                    Toast.makeText(Trash.this, "Note Restored!", Toast.LENGTH_SHORT).show();

                    trashList.remove(position);
                    trashAdapter.notifyItemRemoved(position);

                }else {
                    Toast.makeText(Trash.this, "Note not restored", Toast.LENGTH_SHORT).show();
                }
            });

            TextView tvCrossTrash = dialog.findViewById(R.id.tvCrossTrash);
            tvCrossTrash.setOnClickListener(v -> {
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