package snu.breeze.breeze19;

import android.graphics.drawable.Drawable;

public class ContactPersonData {
    public String name;
    public String role;
    public String phoneNo;
    public int photo;

   public ContactPersonData(String name,String role,String phone,int drawable){
        this.name = name;
        this.role = role;
        this.phoneNo = phone;
        this.photo = drawable;
    }

    public String getName(){
       return name;
    }
    public String getPhoneNo(){
       return phoneNo;
    }
    public String getRole(){
        return role;
    }
    public int getPhoto(){return photo;}


}
