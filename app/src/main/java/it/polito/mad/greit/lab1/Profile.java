package it.polito.mad.greit.lab1;


import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class Profile {

    private String name;
    private String surname;
    private String email;
    private Uri pic;

    private static final String __NAME__ = "Mario";
    private static final String __SURNAME__ = "Mario";
    private static final String __EMAIL__ = "Mario";
    private static final String __BIO__ = "Mario";
    private static Profile instance;

    private Profile(){
        this.name = __NAME__;
        this.surname = __NAME__;
        this.email = __NAME__;
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

    public void commit(SharedPreferences sp){
        SharedPreferences.Editor e = sp.edit();
        e.putString("name", getName());
        e.putString("surname", getSurname());
        e.putString("email", getEmail());
        if(getPic() != null){
            e.putString("pic", getPic().toString());
        }
        e.apply();
    }


    private void loadFromDB(SharedPreferences sp){

        if(sp!= null && sp.contains("name")){
            setName(sp.getString("name",null));
            setSurname(sp.getString("surname",null));
            setEmail(sp.getString("email",null));
            if(sp.getString("pic",null) != null) {
                Log.d("pic", "Setup: pic isn't null");
                setPic(Uri.parse(sp.getString("pic", null)));
            }
        }

    }

}
