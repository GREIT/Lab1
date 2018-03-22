package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class editProfile extends AppCompatActivity {

    Profile profile;
    Button bb;

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
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            profile.setPic(data.getData());
        }
    }
}
