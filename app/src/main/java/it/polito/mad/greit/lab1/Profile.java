package it.polito.mad.greit.lab1;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class Profile {

    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String location;
    private String bio;
    private Uri pic;

    private static final String __NAME__ = "Mario";
    private static final String __SURNAME__ = "Rossi";
    private static final String __NICKNAME__ = "@mariorossi";
    private static final String __EMAIL__ = "mr@gmail.com";
    private static final String __LOCATION__ = "Turin, Italy";
    private static final String __BIO__ = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam metus eros, maximus non ipsum ac, luctus ultricies urna. Suspendisse dignissim volutpat sodales. Nullam tincidunt lectus vitae dui finibus, nec egestas neque venenatis.";
    private static Profile instance;

    private Profile(){
        this.name = __NAME__;
        this.surname = __SURNAME__;
        this.nickname = __NICKNAME__;
        this.email = __EMAIL__;
        this.location = __LOCATION__;
        this.bio = __BIO__;
        this.pic = null;
    }

    public static Profile getInstance(){
        if (instance != null){
            instance = new Profile();
        }
        return instance;
    }

    public static Profile getInstance(SharedPreferences sp){
        if (instance == null){
            instance = new Profile();
            instance.loadFromDB(sp);
        }
        return instance;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {return surname;}

    public String getEmail() {
        return email;
    }

    public Uri getPic() {return pic;}

    public void setPic(Uri pic) {this.pic = pic;}

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }


    public void commit(SharedPreferences sp){
        SharedPreferences.Editor e = sp.edit();
        e.putString("name", getName());
        e.putString("surname", getSurname());
        e.putString("nickname", getNickname());
        e.putString("email", getEmail());
        e.putString("location", getLocation());
        e.putString("bio", getBio());

        if(getPic() != null){
            e.putString("pic", getPic().toString());
        }
        e.apply();
    }


    private void loadFromDB(SharedPreferences sp){

        if(sp!= null && sp.contains("name")){
            setName(sp.getString("name",null));
            setSurname(sp.getString("surname",null));
            setNickname(sp.getString("nickname", null));
            setEmail(sp.getString("email",null));
            setLocation(sp.getString("location", null));
            setBio(sp.getString("bio", null));

            if(sp.getString("pic",null) != null) {
                Log.d("pic", "Setup: pic isn't null");
                setPic(Uri.parse(sp.getString("pic", null)));
            }
        }

    }

}
