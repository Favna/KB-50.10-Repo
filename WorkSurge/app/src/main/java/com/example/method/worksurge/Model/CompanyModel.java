package com.example.method.worksurge.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Method on 06/01/2016.
 */
public class CompanyModel implements Parcelable{
    private String Name;
    private String Information;
    private String Image_url;

    public String getName() {
        return this.Name;
    }

    public String getInformation() {
        return this.Information;
    }

    public String getImageUrl() {
        return this.Image_url;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setInformation(String information) {
        this.Information = information;
    }

    public void setImageUrl(String image_url) {
        this.Image_url = image_url;
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
        dest.writeString (Name);
        dest.writeString (Information);
        dest.writeString (Image_url);
    }
}
