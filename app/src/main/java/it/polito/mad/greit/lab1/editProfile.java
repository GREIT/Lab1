package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class editProfile extends AppCompatActivity {

    Info i;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setup();
        setContentView(R.layout.activity_edit_profile);
        Fill();
        b = findViewById(R.id.apply);
        b.setOnClickListener(view -> SaveInfo());
    }


    void Setup(){
        try {
            i.setName(getIntent().getStringExtra("name"));
            i.setSurname(getIntent().getStringExtra("surname"));
            i.setEmail(getIntent().getStringExtra("email"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void Fill(){
        TextView tv = findViewById(R.id.edit_name);
        tv.setText(i.getName());
        tv = findViewById(R.id.edit_surname);
        tv.setText(i.getSurname());
        tv = findViewById(R.id.edit_email);
        tv.setText(i.getEmail());
    }

    void SaveInfo(){
        TextView tv = findViewById(R.id.edit_name);
        i.setName(tv.getText().toString());
        tv = findViewById(R.id.edit_surname);
        i.setSurname(tv.getText().toString());
        tv = findViewById(R.id.edit_email);
        i.setEmail(tv.getText().toString());



        try {

            Intent swap = new Intent(editProfile.this, showProfile.class);
            swap.putExtra("name", i.getName());
            swap.putExtra("surname",i.getSurname());
            swap.putExtra("email",i.getEmail());
            startActivity(swap);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
