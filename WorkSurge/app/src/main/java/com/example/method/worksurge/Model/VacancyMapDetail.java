package com.example.method.worksurge.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Method on 1/20/2016.
 */
public class VacancyMapDetail implements Parcelable{
    private VacancyModel vacancyModel;
    private String address;

    public VacancyMapDetail() {}

    public VacancyMapDetail (VacancyModel model, String address)
    {
        this.vacancyModel = model;
        this.address = address;
    }

    public VacancyMapDetail (Parcel in)
    {
        this.address = in.readString();
    }

    public VacancyModel getVacancyModel() {
        return this.vacancyModel;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVacancyDetailModel(VacancyModel vacancyModel) {
        this.vacancyModel = vacancyModel;
    }

    public static final Parcelable.Creator<VacancyMapDetail> CREATOR
            = new Parcelable.Creator<VacancyMapDetail>()
    {
        public VacancyMapDetail createFromParcel(Parcel in)
        {

            return new VacancyMapDetail(in);
        }

        public VacancyMapDetail[] newArray (int size)
        {
            return new VacancyMapDetail[size];
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
        dest.writeString (address);
    }

    public void readFromParcel(Parcel in) {
        address = in.readString();
    }
}
