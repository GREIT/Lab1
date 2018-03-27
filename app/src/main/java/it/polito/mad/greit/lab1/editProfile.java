package it.polito.mad.greit.lab1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class editProfile extends AppCompatActivity {

    static final int CAMERA_PERMISSION = 3;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int REQUEST_GALLERY = 1;

    Toolbar t;
    Profile profile;
    Button bb;
    Uri photo;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_edit_profile);
        t = findViewById(R.id.edit_toolbar);
        setSupportActionBar(t);
        Setup();
        Fill();

        bb = findViewById(R.id.revert);
        bb.setOnClickListener(view -> RevertInfo());
        bb = findViewById(R.id.edit_pic);
        bb.setOnClickListener(view -> UploadPic());
/*        bb = findViewById(R.id.snap_pic);
        bb.setOnClickListener(view -> Camera());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.confirm == item.getItemId()) {
            SaveInfo();
            return true;
        } else return super.onOptionsItemSelected(item);
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

        Log.d("mytag", "onActivityResult: resultcode" + resultCode + " Request code " + requestCode);
        if(data == null){
            Log.d("mytag", "onActivityResult: NULL");
        }

        //getContentResolver().takePersistableUriPermission(data.getData(), flags);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            profile.setPic(data.getData());
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            profile.setPic(photo);
        }

    }

    void Camera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File img = File.createTempFile("photo", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            //Log.d("mytag", "Camera: Created file " + img.toString());
            if(img != null){
                photo = FileProvider.getUriForFile(this,"it.polito.mad.greit.lab1",img);
                //Log.d("mytag", "Camera: Entered file provider " + photo.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photo);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}
