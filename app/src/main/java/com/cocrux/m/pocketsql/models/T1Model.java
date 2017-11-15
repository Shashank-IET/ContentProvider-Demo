package com.cocrux.m.pocketsql.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Shashank on 15/11/17.
 */

public class T1Model implements Parcelable, Serializable {

    private String name, aggregate;
    private int roll;

    public T1Model(){}

    public T1Model(Parcel in) {
        name = in.readString();
        aggregate = in.readString();
        roll = in.readInt();
    }

    public static final Creator<T1Model> CREATOR = new Creator<T1Model>() {
        @Override
        public T1Model createFromParcel(Parcel in) {
            return new T1Model(in);
        }

        @Override
        public T1Model[] newArray(int size) {
            return new T1Model[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(aggregate);
        dest.writeInt(roll);
    }
}

