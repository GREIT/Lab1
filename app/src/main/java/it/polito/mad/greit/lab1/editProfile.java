package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class editProfile extends AppCompatActivity {

    Info i;
    Button bb;
    Uri old_pic;

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
        i = new Info();
        try {
            i.setName(getIntent().getStringExtra("name"));
            i.setSurname(getIntent().getStringExtra("surname"));
            i.setEmail(getIntent().getStringExtra("email"));
            if(getIntent().getStringExtra("pic") != null) {
                i.setPic(Uri.parse(getIntent().getStringExtra("pic")));
                old_pic = i.getPic();
            }
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

            SharedPreferences sp = getSharedPreferences("storage",MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.putString("name",i.getName());
            e.putString("surname",i.getSurname());
            e.putString("email",i.getEmail());
            if(i.getPic() != null){
                e.putString("pic",i.getPic().toString());
            }
            e.apply();

            Intent swap = new Intent(editProfile.this, showProfile.class);
            swap.putExtra("name", i.getName());
            swap.putExtra("surname",i.getSurname());
            swap.putExtra("email",i.getEmail());
            if(i.getPic() != null){
                swap.putExtra("pic",i.getPic().toString());
            }
            swap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(swap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RevertInfo(){
        TextView tv = findViewById(R.id.edit_name);
        tv.setText(i.getName());
        tv = findViewById(R.id.edit_surname);
        tv.setText(i.getSurname());
        tv = findViewById(R.id.edit_email);
        tv.setText(i.getEmail());
        Intent swap = new Intent(editProfile.this, showProfile.class);
        swap.putExtra("name", i.getName());
        swap.putExtra("surname",i.getSurname());
        swap.putExtra("email",i.getEmail());
        if(old_pic != null) {
            swap.putExtra("pic", old_pic);
        }
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
            i.setPic(data.getData());
        }
    }
}
