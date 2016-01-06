package com.example.method.worksurge.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Method on 06/01/2016.
 */
public class VacancyModel implements Parcelable{
    private String Title;
    private String Undertitle;
    private String Details;
    private String URL = "google";

    public VacancyModel() {}

    public VacancyModel (String t, String u, String d, String url)
    {
        this.Title = t;
        this.Undertitle = u;
        this.Details = d;
        this.URL = url;
    }

    public VacancyModel (Parcel in)
    {
        this.Title = in.readString ();
        this.Undertitle = in.readString ();
        this.Details = in.readString ();
        this.URL = in.readString ();
    }

    public String getTitle() {
        return this.Title;
    }

    public String getUndertitle() {
        return this.Undertitle;
    }

    public String getDetails() {
        return this.Details;
    }

    public String getURL() {
        return this.URL;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setUndertitle(String undertitle) {
        this.Undertitle = undertitle;
    }

    public void setDetails(String details) {
        this.Details = details;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


    public static final Parcelable.Creator<VacancyModel> CREATOR
            = new Parcelable.Creator<VacancyModel>()
    {
        public VacancyModel createFromParcel(Parcel in)
        {
            return new VacancyModel(in);
        }

        public VacancyModel[] newArray (int size)
        {
            return new VacancyModel[size];
        }
    };

    @Override
    public int describeContents ()
    {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags)
    {
        dest.writeString (Title);
        dest.writeString (Undertitle);
        dest.writeString (Details);
        dest.writeString (URL);
    }
}
