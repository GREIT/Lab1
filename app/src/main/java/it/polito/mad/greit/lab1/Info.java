package it.polito.mad.greit.lab1;


import android.net.Uri;

public class Info {

    private String Name;
    private String Surname;
    private String Email;
    private Uri Pic;

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {return Surname;}

    public String getEmail() {
        return Email;
    }

    public Uri getPic() {return Pic;}

    public void setPic(Uri pic) {Pic = pic;}

}
