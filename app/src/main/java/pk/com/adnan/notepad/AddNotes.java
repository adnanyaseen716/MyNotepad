package pk.com.adnan.notepad;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotes extends AppCompatActivity {
    TextView tvAddImage , tvBack , tvTotalCharacters , tvLastEdited , tvSaveNote;
    ImageView addImage;
    EditText etEnterDescription , etEnterTitle ;
     String lastEdited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_notes);

        tvAddImage = findViewById(R.id.tvAddImage);
        tvBack = findViewById(R.id.tvBack);
        addImage = findViewById(R.id.ivImagePlace);
        tvTotalCharacters = findViewById(R.id.tvTotalCharacters);
        tvLastEdited = findViewById(R.id.tvLastEdited);
        tvSaveNote = findViewById(R.id.tvSaveNote);
        etEnterDescription = findViewById(R.id.etEnterDescription);

        etEnterTitle = findViewById(R.id.etEnterTitle);




   tvSaveNote.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           String description = etEnterDescription.getText().toString();
           String title = etEnterTitle.getText().toString();

           Note note = new Note(title, description);
           DB db = DB.getInstance(AddNotes.this);

           if (db.insertNote(note,AddNotes.this)) {

               Toast.makeText(AddNotes.this, "Record inserted Successfully!", Toast.LENGTH_LONG).show();

               finish();
           }


       }
   });

        tvBack.setOnClickListener(v -> {
            finish();
        });

        //---------------------------------- CONTRACT --------------------------------------


        tvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(AddNotes.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_add_image_dlg);

                TextView addCameraImg = dialog.findViewById(R.id.tvAddCameraImg);
                TextView addGalleryImg = dialog.findViewById(R.id.tvAddGalleryImg);

                addGalleryImg.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    openGallery();

                });

                addCameraImg.setOnClickListener(v12 -> {
                    dialog.dismiss();
                    openCamera();
                });

                TextView tvCrossCamera = dialog.findViewById(R.id.tvCrossCamera);
                tvCrossCamera.setOnClickListener(v2 -> {
                    dialog.dismiss();
                });


                dialog.show();

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);


            }
        });





        //------------------------------------------------- TOTAL CHARACTERS ------------------------

        etEnterDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = s.length();
                tvTotalCharacters.setText("Total Characters :  "+count);
            }
        });




        //---------------------------------------  LAST EDITED FUNCTION CALL  -------------------------------------------------

        lastEditedFun();

        //----------------------------------------------------------------------------------------

    }

    //----------------------------------------  GALLERY -------------------------------------------
    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Uri uriImage = result.getData().getData();
                    addImage.setImageURI(uriImage);
                }
            });

    //---------------------------------- CAMERA ---------------------------------
    public void openCamera (){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bundle bundle  = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    addImage.setImageBitmap(bitmap);
                }
            });

    //----------------------------------------------------------------------------


    //-----------------EXTRA BUTTONS--------------------------------------
    public void boldText (View view){
        Spannable spannableString = new SpannableStringBuilder(etEnterDescription.getText());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                etEnterDescription.getSelectionStart(),
                etEnterDescription.getSelectionEnd(),
                0);


        etEnterDescription.setText(spannableString);
    }

    public void italicText (View view){
        Spannable spannableString = new SpannableStringBuilder(etEnterDescription.getText());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                etEnterDescription.getSelectionStart(),
                etEnterDescription.getSelectionEnd(),
                0);


        etEnterDescription.setText(spannableString);
    }

    public void underlineText (View view){
        Spannable spannableString = new SpannableStringBuilder(etEnterDescription.getText());
        spannableString.setSpan(new UnderlineSpan(),
                etEnterDescription.getSelectionStart(),
                etEnterDescription.getSelectionEnd(),
                0);

        etEnterDescription.setText(spannableString);
    }


    public void noFormatText (View view){
        String stringText = etEnterDescription.getText().toString();
        etEnterDescription.setText(stringText);
    }




    public void lastEditedFun(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        lastEdited = "  |  "+"Last Edited : "+dateFormat.format(calendar.getTime());

        tvLastEdited.setText(lastEdited);

    }

}