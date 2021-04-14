package my.e.lists.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item  {

    private String title, description;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
