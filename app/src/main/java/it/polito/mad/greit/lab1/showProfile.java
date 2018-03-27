package it.polito.mad.greit.lab1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;


public class showProfile extends AppCompatActivity {

    final int STORAGE_PERMISSION = 1;
    Toolbar t;
    Profile profile;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_show_profile);
        t = findViewById(R.id.show_toolbar);
        setSupportActionBar(t);
        Setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.edit == item.getItemId()) {
            Intent swap = new Intent(showProfile.this, editProfile.class);

            swap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(swap);
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void Setup() {

        if (ContextCompat.checkSelfPermission(showProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(showProfile.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        }

        profile = Profile.getInstance(getSharedPreferences("storage", MODE_PRIVATE));

        TextView tv = findViewById(R.id.name);
        tv.setText(profile.getName());
        tv = findViewById(R.id.surname);
        tv.setText(profile.getSurname());
        tv = findViewById(R.id.email);
        tv.setText(profile.getEmail());
        if (profile.getPic() != null) {
            ImageView iw = findViewById(R.id.pic);
            iw.setImageURI(profile.getPic());
            //Log.d("pic", "Setup: pic isn't null");
        } else {
            ImageView iw = findViewById(R.id.pic);
            iw.setImageResource(R.mipmap.ic_launcher);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}
