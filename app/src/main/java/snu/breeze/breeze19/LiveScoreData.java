package snu.breeze.breeze19;

import android.os.Parcel;
import android.os.Parcelable;

public class LiveScoreData implements Parcelable {

    private String team1;
    private String team2;
    private String score1;
    private String score2;
    private String key;
    private int isLive;
    private String sportName;

    public static final LiveScoreData.ClassLoaderCreator<LiveScoreData> CREATOR = new Parcelable.ClassLoaderCreator<LiveScoreData>(){
        @Override
        public LiveScoreData createFromParcel(Parcel in, ClassLoader loader) {
            return new LiveScoreData(in);
        }

        @Override
        public LiveScoreData createFromParcel(Parcel in){
            return new LiveScoreData(in);
        }

        @Override
        public LiveScoreData[] newArray(int size){
            return new LiveScoreData[size];
        }
    };

    public LiveScoreData(){
    }

    public LiveScoreData(String team1,String team2,String score1,String score2,String sportName){
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.sportName = sportName;
    }

    private LiveScoreData(Parcel in){
        team1 = in.readString();
        team2 = in.readString();
        score1 = in.readString();
        score2 = in.readString();
        sportName = in.readString();
        isLive = in.readInt();
        key = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out,int flags){
        out.writeString(team1);
        out.writeString(team2);
        out.writeString(score1);
        out.writeString(score2);
        out.writeString(sportName);
        out.writeInt(isLive);
        out.writeString(key);
    }

    @Override
    public String toString() {
        return getTeam1() + " " + getScore1() + " : " + getTeam2() + " " + getScore2();
    }

    public String getTeam1(){
        return team1;
    }

    public String getTeam2(){
        return team2;
    }

    public String getScore1() {
        return score1;
    }

    public String getScore2(){
        return score2;
    }

    public String getSportName(){
        return sportName;
    }

    public int getisLive(){
        return isLive;
    }

    public String getKey(){
        return key;
    }

    public void setTeam1(String team1){
        this.team1 = team1;
    }

    public void setTeam2(String team2){
        this.team2 = team2;
    }

    public void setScore1(String score1){
        this.score1 = score1;
    }

    public void setScore2(String score2){
        this.score2 = score2;
    }

    public void setisLive(int isLive) {
        this.isLive=isLive;

    }
    public void setSportName(String sportName){
        this.sportName = sportName;
    }

    public void setKey(String key){
        this.key = key;
    }
}
