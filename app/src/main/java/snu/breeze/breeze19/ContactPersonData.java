package snu.breeze.breeze19;

public class ContactPersonData {
    public String name;
    public String role;
    public String phoneNo;

   public ContactPersonData(String name,String role,String phone){
        this.name = name;
        this.role = role;
        this.phoneNo = phone;
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


}
