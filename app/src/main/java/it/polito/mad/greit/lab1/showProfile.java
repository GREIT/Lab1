package it.polito.mad.greit.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class showProfile extends AppCompatActivity {
  private Profile P;
  private ImageView avatarImageView;
  private TextView nameTextView;
  private TextView emailTextView;
  private TextView bioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
    
        P = new Profile(getApplicationContext());
  
        avatarImageView = findViewById(R.id.avatarImage_showProfile);
        avatarImageView.setImageResource(P.getAvatar());
        
        nameTextView = findViewById(R.id.nameText_ShowProfile);
        nameTextView.setText(P.getName());
        
        emailTextView = findViewById(R.id.emailText_showProfile);
        emailTextView.setText(P.getEmail());
        
        bioTextView = findViewById(R.id.bioText_showProfile);
        bioTextView.setText(P.getBio());
        
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_profile, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_modify_Profile:
              Intent intent = new Intent(this, editProfile.class);
              startActivity(intent);
              return true;
            
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
            
        }
    }
    
    
}
