package snu.breeze.breeze19;

import android.os.Parcel;
import android.os.Parcelable;

public class LiveEventsData implements Parcelable {

    private String heading;
    private String content;
    private String key;

    public static final LiveEventsData.ClassLoaderCreator<LiveEventsData> CREATOR = new Parcelable.ClassLoaderCreator<LiveEventsData>(){
        @Override
        public LiveEventsData createFromParcel(Parcel in, ClassLoader loader) {
            return new LiveEventsData(in);
        }

        @Override
        public LiveEventsData createFromParcel(Parcel in){
            return new LiveEventsData(in);
        }

        @Override
        public LiveEventsData[] newArray(int size){
            return new LiveEventsData[size];
        }
    };

    public LiveEventsData(){

    }

    public LiveEventsData(String heading,String content){
        this.heading = heading;
        this.content = content;
    }

    private LiveEventsData(Parcel in){
        heading = in.readString();
        content = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(heading);
        out.writeString(content);
        out.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return getHeading() + getContent();
    }

    public String getHeading(){
        return heading;
    }

    public String getContent(){
        return content;
    }

    public String getKey(){
        return key;
    }

    public void setHeading(String heading){
        this.heading = heading;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setKey(String key){
        this.key = key;
    }

}
