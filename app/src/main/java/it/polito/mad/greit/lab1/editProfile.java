package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

public class editProfile extends AppCompatActivity {
  private Profile P;
  private ImageView avatarImageView;
  private EditText nameTextView;
  private EditText emailTextView;
  private EditText bioTextView;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
  
    P = new Profile(getApplicationContext());
    
    avatarImageView = findViewById(R.id.avatarImage_editProfile);
    avatarImageView.setImageResource(P.getAvatar());
  
    nameTextView = findViewById(R.id.nameText_editProfile);
    nameTextView.setText(P.getName());
  
    nameTextView.addTextChangedListener(new TextWatcher() {
    
      public void afterTextChanged(Editable s) {
      
        P.setName(nameTextView.getText().toString());
      
      }
    
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    
      public void onTextChanged(CharSequence s, int start, int before, int count) {}
    });
  
    emailTextView = findViewById(R.id.emailText_editProfile);
    emailTextView.setText(P.getEmail());
  
    bioTextView = findViewById(R.id.bioText_editProfile);
    bioTextView.setText(P.getBio());
  }
  
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_edit_profile, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_accept_modify_Profile:
        P.saveProfileJSONOnFile();
        Intent intentOK = new Intent(this, showProfile.class);
        startActivity(intentOK);
        return true;
  
      case R.id.menu_discard_modify_Profile:
        Intent intentNOT = new Intent(this, showProfile.class);
        startActivity(intentNOT);
        return true;
      
      default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
      
    }
  }
  
  
}
