package com.labProject22.FlightTracker.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties
public class TrackerPlane {
    
    @Id
    private float longitude;        // WGS-84 longitude in decimal degrees. Can be null.
    private float latitude;         // WGS-84 latitude in decimal degrees. Can be null.

    public TrackerPlane(){}
    
    public TrackerPlane(float longitude, float latitude){
        
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Plane{" +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}' + "\n";
    }

}
