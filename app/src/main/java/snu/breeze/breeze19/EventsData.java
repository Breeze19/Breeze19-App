package snu.breeze.breeze19;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class EventsData implements Parcelable {
    private String eventsName;
    private String eventsDetails;
    private String eventVenue;
    private String eventDate;
    private String eventContact;
    private String eventCategory;
    private String key;

    public EventsData(){

    }

    private EventsData(Parcel in){
        eventsName = in.readString();
        eventsDetails = in.readString();
        eventVenue = in.readString();
        eventDate = in.readString();
        eventContact = in.readString();
        eventCategory = in.readString();
        key = in.readString();
    }

    public EventsData(String eventsName, String eventsDetails, String eventDate, String eventVenue,String eventContact,String eventCategory){
        this.eventsName = eventsName;
        this.eventsDetails = eventsDetails;
        this.eventVenue = eventVenue;
        this.eventDate = eventDate;
        this.eventContact = eventContact;
        this.eventCategory = eventCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final EventsData.ClassLoaderCreator<EventsData> CREATOR = new Parcelable.ClassLoaderCreator<EventsData>(){
        @Override
        public EventsData createFromParcel(Parcel in, ClassLoader loader) {
            return new EventsData(in);
        }

        @Override
        public EventsData createFromParcel(Parcel in){
            return new EventsData(in);
        }

        @Override
        public EventsData[] newArray(int size){
            return new EventsData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(eventsName);
        out.writeString(eventsDetails);
        out.writeString(eventVenue);
        out.writeString(eventDate);
        out.writeString(eventContact);
        out.writeString(eventCategory);
        out.writeString(key);
    }

    @Override
    public String toString() {
        return geteventDate() + geteventsName();
    }


    public String geteventsName(){
        return eventsName;
    }

    public String geteventsDetails(){
        return eventsDetails;
    }

    public String geteventVenue(){
        return eventVenue;
    }

    public String geteventDate(){
        return eventDate;
    }

    public String geteventContact(){
        return eventContact;
    }

    public String getEventCategory(){
        return eventCategory;
    }

    public String getKey(){
        return key;
    }


    public void seteventsName(String eventsName){
        this.eventsName = eventsName;
    }

    public void seteventsDetails(String eventsDetails){
        this.eventsDetails = eventsDetails;
    }


    public ArrayList getAllDetails() {
        ArrayList<String> details = new ArrayList<>();
        details.add("Event Details \t-\t " + eventsDetails);
        details.add("Event name \t-\t " + eventsName);
        details.add("Event Date \t-\t " + eventDate);
        details.add("Event Venue \t-\t " + eventVenue);
        return details;
    }

    public void seteventVenue(String eventVenue){
        this.eventVenue = eventVenue;
    }

    public void seteventDate(String eventDate){
        this.eventDate = eventDate;
    }

    public void setEventCategory(String category){
        this.eventCategory = category;
    }

    public void setKey(String key){
        this.key = key;
    }

}
