package com.imceits.android.myjobtracker.data;

import android.location.Geocoder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Jobs implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("job-id")
    @Expose
    private String jobId;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("geolocation")
    @Expose
    private GeoLocation geoLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
}
