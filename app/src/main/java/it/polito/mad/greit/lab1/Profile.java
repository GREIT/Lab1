package it.polito.mad.greit.lab1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Carmine on 21/03/2018.
 */

public class Profile {
  private Context context;
  private JSONObject profileJSON;
  private String name;
  private String surname;
  private String nickname;
  private String location;
  private String bio;
  private String email;
  private Uri avatar;
  
  public Profile(Context C) {
    this.context = C;
    
    try {
      this.profileJSON = new JSONObject(loadJSONFromFile());
      this.name = profileJSON.getString("name");
      this.surname = profileJSON.getString("surname");
      this.nickname = profileJSON.getString("nickname");
      this.location = profileJSON.getString("location");
      this.bio = profileJSON.getString("bio");
      this.email = profileJSON.getString("email");
      this.avatar = Uri.parse(profileJSON.getString("avatar"));
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String newName) {
    try {
      profileJSON.put("name", newName);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.name = newName;
  }
  
  public String getSurname() {
    return surname;
  }
  
  public void setSurname(String newSurname) {
    try {
      profileJSON.put("surname", newSurname);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.surname = newSurname;
  }
  
  public String getNickname() {
    return nickname;
  }
  
  public void setNickname(String newNickname) {
    try {
      profileJSON.put("nickname", newNickname);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.nickname = newNickname;
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String newLocation) {
    try {
      profileJSON.put("location", newLocation);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.location = newLocation;
  }
  
  public String getBio() {
    return bio;
  }
  
  public void setBio(String newBio) {
    try {
      profileJSON.put("bio", newBio);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.bio = newBio;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String newEmail) {
    try {
      profileJSON.put("email", newEmail);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.email = newEmail;
  }
  
  public Uri getAvatar() {
    return avatar;
  }
  
  public void setAvatar(Uri newAvatar) {
    try {
      profileJSON.put("avatar", newAvatar.toString());
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.avatar = newAvatar;
  }
  
  public void createDefaultProfileJSON() {
    FileOutputStream outputStream;
    
    try {
      JSONObject J = new JSONObject();
      J.put("name", "Default_Name");
      J.put("bio", "Default_Bio");
      J.put("email", "Default_Email");
      J.put("surname", "Default_Surname");
      J.put("nickname", "Default_Nickname");
      J.put("location", "Default_location");
      J.put("avatar", null);
      outputStream = context.openFileOutput("profile.json", MODE_PRIVATE);
      outputStream.write(J.toString().getBytes());
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void saveProfileJSONOnFile() {
    FileOutputStream outputStream;
    try {
      outputStream = context.openFileOutput("profile.json", MODE_PRIVATE);
      outputStream.write(profileJSON.toString().getBytes());
      outputStream.close();
    } catch (Exception E) {
      E.printStackTrace();
    }
  }
  
  private String loadJSONFromFile() {
    String json = "";
    
    try {
      
      InputStream inputStream =  context.openFileInput("profile.json");
      if ( inputStream != null ) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString = "";
        StringBuilder stringBuilder = new StringBuilder();
        
        while ( (receiveString = bufferedReader.readLine()) != null ) {
          stringBuilder.append(receiveString);
        }
        
        inputStream.close();
        json = stringBuilder.toString();
      }
    }
    catch (FileNotFoundException e) {
      createDefaultProfileJSON();
      Log.e("login activity", "ProfileJSON not found: " + e.toString());
    } catch (IOException e) {
      Log.e("login activity", "Can not read ProfileJSON: " + e.toString());
    }
    
    return json;
  }
}
