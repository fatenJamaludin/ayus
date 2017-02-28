package com.app.tbd.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class DropDownItem implements Parcelable {
    private int id;
    private String code;
    private String text;
    private Object tag;

    public DropDownItem() {

    }

    private DropDownItem(Parcel in) {
        code = in.readString();
        text = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(code);
    }

    public static final Creator<DropDownItem> CREATOR = new Creator<DropDownItem>() {

        @Override
        public DropDownItem createFromParcel(Parcel source) {
            return new DropDownItem(source);
        }

        @Override
        public DropDownItem[] newArray(int size) {
            return new DropDownItem[size];
        }
    };
}
