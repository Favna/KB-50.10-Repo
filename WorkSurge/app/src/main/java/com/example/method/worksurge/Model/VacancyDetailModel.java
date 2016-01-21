package com.example.method.worksurge.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Method on 1/13/2016.
 */
public class VacancyDetailModel implements Parcelable {
    private String Title;
    private String Detail;
    private String Company;
    private List<String> Meta;
    private String Telefoon = "";
    private String Url;

    public VacancyDetailModel() {}

    public VacancyDetailModel (String t, String d, String c, List<String> m, String tf, String u)
    {
        this.Title = t;
        this.Detail = d;
        this.Company = c;
        this.Meta = m;
        this.Telefoon = tf;
        this.Url = u;
    }

    public VacancyDetailModel (Parcel in)
    {
        this.Meta = new ArrayList<String>();
        this.Title = in.readString ();
        this.Detail = in.readString ();
        this.Company = in.readString();
        in.readStringList(Meta);
        this.Telefoon = in.readString();
        this.Url = in.readString();
    }

    public String getTitle() {
        return this.Title;
    }

    public String getDetail() {
        return this.Detail;
    }

    public String getCompany() {
        return this.Company;
    }

    public List<String> getMeta() {
        return this.Meta;
    }

    public String getTelefoon() {
        return this.Telefoon;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setDetail(String detail) {
        this.Detail = Detail;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public void setMeta(List<String> meta) {
        this.Meta = meta;
    }

    public void setTelefoon(String telefoon) {
        this.Telefoon = telefoon;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public static final Parcelable.Creator<VacancyDetailModel> CREATOR
            = new Parcelable.Creator<VacancyDetailModel>()
    {
        public VacancyDetailModel createFromParcel(Parcel in)
        {
            return new VacancyDetailModel(in);
        }

        public VacancyDetailModel[] newArray (int size)
        {
            return new VacancyDetailModel[size];
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
        dest.writeString (Detail);
        dest.writeString (Company);
        dest.writeStringList(Meta);
        dest.writeString (Telefoon);
        dest.writeString (Url);
    }

    public void readFromParcel(Parcel in) {
        this.Meta = new ArrayList<String>();
        Title = in.readString();
        Detail = in.readString();
        Company = in.readString();
        in.readStringList(Meta);
        Telefoon = in.readString();
        Url = in.readString();
    }
}
