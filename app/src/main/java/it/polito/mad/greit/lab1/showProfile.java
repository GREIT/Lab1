package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;


public class showProfile extends AppCompatActivity {

    Toolbar t;
    Info i;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_show_profile);
        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        Setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(R.id.edit == item.getItemId()) {
            Intent swap = new Intent(showProfile.this, editProfile.class);
            swap.putExtra("name",i.getName());
            swap.putExtra("surname",i.getSurname());
            swap.putExtra("email",i.getEmail());
            if(i.getPic() != null) {
                swap.putExtra("pic",i.getPic().toString());
            }
            swap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(swap);
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    private void Setup(){

        i = new Info();
        SharedPreferences sp = getSharedPreferences("storage",MODE_PRIVATE);

        if(getIntent().getStringExtra("name") != null){
            i.setName(getIntent().getStringExtra("name"));
            i.setSurname(getIntent().getStringExtra("surname"));
            i.setEmail(getIntent().getStringExtra("email"));
            if(getIntent().getStringExtra("pic") != null) {
                i.setPic(Uri.parse(getIntent().getStringExtra("pic")));
            }
        }
        else if(sp.contains("name")){
            i.setName(sp.getString("name",null));
            i.setSurname(sp.getString("surname",null));
            i.setEmail(sp.getString("email",null));
            if(sp.getString("pic",null) != null) {
                Log.d("pic", "Setup: pic isn't null");
                i.setPic(Uri.parse(sp.getString("pic", null)));
            }
        }

        else{
            i.setName("Mario");
            i.setSurname("Rossi");
            i.setEmail("mr@gmail.com");
        }

        TextView tv = findViewById(R.id.name);
        tv.setText(i.getName());
        tv = findViewById(R.id.surname);
        tv.setText(i.getSurname());
        tv = findViewById(R.id.email);
        tv.setText(i.getEmail());
        if(i.getPic() != null){
            ImageView iw = findViewById(R.id.pic);
            iw.setImageURI(i.getPic());
            Log.d("pic", "Setup: pic isn't null");
        }
        else{
            ImageView iw = findViewById(R.id.pic);
            iw.setImageResource(R.mipmap.ic_launcher);
        }

    }
}
