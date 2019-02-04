package snu.breeze.breeze19;

import android.widget.TextView;

public class pronightData {

    private String Name;
    private int BackgroundNo;
    private String key;


    public pronightData(String name,int number){
        this.Name = name;
        this.BackgroundNo = number;
    }

    public String getName(){
        return Name;
    }
    public String getKey(){
        return key;
    }
    public int getBackgroundNo(){
        return BackgroundNo;
    }

    public void setName(String name){
        this.Name = name;
    }
    public void setKey(String name){
        this.key = name;
    }
    public void setBackgroundNo(int backgrounNo){
        this.BackgroundNo = backgrounNo;
    }
}
