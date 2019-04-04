package com.android.goalgeta2.models;

import com.google.gson.annotations.SerializedName;

public class GoalResponse {
    @SerializedName("0")
    private float aFloat;

    @SerializedName("data")
    Data DataObject;


    // Getter Methods

    public float getaFloat() {
        return 0;
    }

    public Data getData() {
        return DataObject;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public void setDataObject(Data dataObject) {
        DataObject = dataObject;
    }

    public Data getDataObject() {
        return DataObject;
    }
}
