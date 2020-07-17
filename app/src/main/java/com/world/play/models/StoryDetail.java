package com.world.play.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryDetail implements Parcelable {

    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("descendants")
    @Expose
    private long descendants;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("kids")
    @Expose
    private List<Long> kids = null;
    @SerializedName("score")
    @Expose
    private long score;
    @SerializedName("time")
    @Expose
    private long time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    protected StoryDetail(Parcel in) {
        by = in.readString();
        descendants = in.readLong();
        id = in.readLong();
        score = in.readLong();
        time = in.readLong();
        title = in.readString();
        type = in.readString();
        url = in.readString();
    }

    public static final Creator<StoryDetail> CREATOR = new Creator<StoryDetail>() {
        @Override
        public StoryDetail createFromParcel(Parcel in) {
            return new StoryDetail(in);
        }

        @Override
        public StoryDetail[] newArray(int size) {
            return new StoryDetail[size];
        }
    };

    public String getBy() {
        return by;
    }

    public long getDescendants() {
        return descendants;
    }

    public long getId() {
        return id;
    }

    public List<Long> getKids() {
        return kids;
    }

    public long getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(by);
        parcel.writeLong(descendants);
        parcel.writeLong(id);
        parcel.writeLong(score);
        parcel.writeLong(time);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(url);
    }
}
