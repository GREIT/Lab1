package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.*;
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
            swap.putExtra("name", i.getName());
            swap.putExtra("surname",i.getSurname());
            swap.putExtra("email",i.getEmail());
            startActivity(swap);
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    private void Setup(){
        if(getIntent() != null){
            i.setName(getIntent().getStringExtra("name"));
            i.setSurname(getIntent().getStringExtra("surname"));
            i.setEmail(getIntent().getStringExtra("email"));
            TextView tv = findViewById(R.id.name);
            tv.setText(i.getName());
            tv = findViewById(R.id.surname);
            tv.setText(i.getSurname());
            tv = findViewById(R.id.email);
            tv.setText(i.getEmail());
        }
        else{
            try{


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
