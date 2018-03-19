package it.polito.mad.greit.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class showProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        Button save = findViewById(R.id.save);
     //   save.setOnClickListener( v -> createProfile() );
    }



}
