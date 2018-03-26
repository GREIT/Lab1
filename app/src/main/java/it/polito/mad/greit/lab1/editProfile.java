package it.polito.mad.greit.lab1;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class editProfile extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int REQUEST_GALLERY = 1;
    Profile profile;
    Button bb;
    Uri imguri;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        Setup();
        setContentView(R.layout.activity_edit_profile);
        Fill();
        bb = findViewById(R.id.apply);
        bb.setOnClickListener(view -> SaveInfo());
        bb = findViewById(R.id.revert);
        bb.setOnClickListener(view -> RevertInfo());
        bb = findViewById(R.id.edit_pic);
        bb.setOnClickListener(view -> UploadPic());
        bb = findViewById(R.id.snap_pic);
        bb.setOnClickListener(view -> Camera());
    }


    void Setup(){
        profile = Profile.getInstance(getSharedPreferences("storage",MODE_PRIVATE));
    }

    void Fill(){
        TextView tv = findViewById(R.id.edit_name);
        tv.setText(profile.getName());
        tv = findViewById(R.id.edit_surname);
        tv.setText(profile.getSurname());
        tv = findViewById(R.id.edit_email);
        tv.setText(profile.getEmail());
    }

    void SaveInfo(){
        TextView tv = findViewById(R.id.edit_name);
        profile.setName(tv.getText().toString());
        tv = findViewById(R.id.edit_surname);
        profile.setSurname(tv.getText().toString());
        tv = findViewById(R.id.edit_email);
        profile.setEmail(tv.getText().toString());

        try {
            profile.commit( getSharedPreferences("storage",MODE_PRIVATE));
            Intent swap = new Intent(editProfile.this, showProfile.class);
            swap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(swap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RevertInfo(){
        Intent swap = new Intent(editProfile.this, showProfile.class);
        swap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(swap);
    }


    private void UploadPic(){
            Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            gallery.setType("image/*");
            gallery.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //flags = gallery.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(gallery,REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //getContentResolver().takePersistableUriPermission(data.getData(), flags);
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK){
            profile.setPic(data.getData());
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Log.d("mytag", data.getDataString());
            profile.setPic(this.imguri);
        }
    }

    void Camera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        imguri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"pic.jpg"));
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,this.imguri);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }
}
