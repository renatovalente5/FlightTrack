package com.labProject22.FlightTracker.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties
public class TrackerPlane {
    

    private Long Id;
    @Id
    private String icao;            // Unique ICAO 24-bit address of the transponder in hex string representation.
    private String origin_country;  // Country name inferred from the ICAO 24-bit address.
    private float longitude;        // WGS-84 longitude in decimal degrees. Can be null.
    private float latitude;         // WGS-84 latitude in decimal degrees. Can be null.

    public TrackerPlane(){}

    public TrackerPlane(String icao, String origin_country, float longitude, float latitude) {
        this.icao = icao;
        this.origin_country = origin_country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
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

    @java.lang.Override
    public java.lang.String toString() {
        return "TrackerPlane{" +
                "Id=" + Id +
                ", icao='" + icao + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
