package it.polito.mad.greit.lab1;

import android.content.Context;
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
  private String bio;
  private String email;
  private String avatar;
  
  public Profile(Context C) {
    this.context = C;
  
    try {
      this.profileJSON = new JSONObject(loadJSONFromFile());
      this.name = profileJSON.getString("name");
      this.bio = profileJSON.getString("bio");
      this.email = profileJSON.getString("email");
      this.avatar = profileJSON.getString("avatar");
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
  
  public String getAvatar() {
    return avatar;
  }
  
  public void setAvatar(String newAvatar) {
    try {
      profileJSON.put("avatar", newAvatar);
    } catch (JSONException JE) {
      JE.printStackTrace();
    }
    this.avatar = newAvatar;
  }
  
  private void createProfileJSON() {
    FileOutputStream outputStream;
  
    try {
      JSONObject J = new JSONObject();
      J.put("name", "Default_Name");
      J.put("bio", "Default_Bio");
      J.put("email", "Default_Email");
      J.put("avatar", "Default_Avatar");
      outputStream = context.openFileOutput("profile.json", MODE_PRIVATE);
      outputStream.write(J.toString().getBytes());
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void saveProfileJSONOnFile() {
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
      createProfileJSON();
      Log.e("login activity", "ProfileJSON not found: " + e.toString());
    } catch (IOException e) {
      Log.e("login activity", "Can not read ProfileJson: " + e.toString());
    }
  
    return json;
  }
}
