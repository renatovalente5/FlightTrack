package com.labProject22.FlightTracker.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties
public class PlaneIn {
    
//    @Id
//    @SequenceGenerator(
//            name = "plane_sequence",
//            sequenceName = "plane_sequence",
//            allocationSize = 1
//    )
//
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "plane_sequence"
//    )


//    private Long Id;
    @Id
    private String icao;            // Unique ICAO 24-bit address of the transponder in hex string representation.
    private String callsign;        // Callsign of the vehicle (8 chars). Can be null if no callsign has been received.
    private String origin_country;  // Country name inferred from the ICAO 24-bit address.
    private int time_position;      // Unix timestamp (seconds) for the last position update. Can be null if no position report was received by OpenSky within the past 15s.
    private int last_contact;       // Unix timestamp (seconds) for the last update in general. This field is updated for any new, valid message received from the transponder.
    private float longitude;        // WGS-84 longitude in decimal degrees. Can be null.
    private float latitude;         // WGS-84 latitude in decimal degrees. Can be null.
    private float baro_altitude;    // Barometric altitude in meters. Can be null.
    private boolean on_ground;      // Boolean value which indicates if the position was retrieved from a surface position report.
    private float velocity;         // Velocity over ground in m/s. Can be null.
    private float true_track;       // True track in decimal degrees clockwise from north (north=0°). Can be null.
    private float vertical_rate;    // Vertical rate in m/s. A positive value indicates that the airplane is climbing, a negative value indicates that it descends. Can be null.
    private int[] sensors;          // IDs of the receivers which contributed to this state vector. Is null if no filtering for sensor was used in the request.
    private float geo_altitude;     // Geometric altitude in meters. Can be null.
    private String squawk;          // The transponder code aka Squawk. Can be null.
    private boolean spi;            // Whether flight status indicates special purpose indicator.
    private int position_source;    // Origin of this state’s position: 0 = ADS-B, 1 = ASTERIX, 2 = MLAT

    public PlaneIn(){}
    
    public PlaneIn(String icao, String callsign, String origin_country, int time_position,
                 int last_contact, float longitude, float latitude, float baro_altitude,
                 boolean on_ground, float velocity, float true_track, float vertical_rate,
                 int[] sensors, float geo_altitude, String squawk, boolean spi, int position_source){
        
        this.icao = icao;
        this.callsign = callsign;
        this.origin_country = origin_country;
        this.time_position = time_position;
        this.last_contact = last_contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.baro_altitude = baro_altitude;
        this.on_ground = on_ground;
        this.velocity = velocity;
        this.true_track = true_track;
        this.vertical_rate = vertical_rate;
        this.sensors = sensors;
        this.geo_altitude = geo_altitude;
        this.squawk = squawk;
        this.spi = spi;
        this.position_source = position_source;
    }

//    public PlaneIn(Long id, String icao, String callsign, String origin_country, int time_position,
//             int last_contact, float longitude, float latitude, float baro_altitude,
//             boolean on_ground, float velocity, float true_track, float vertical_rate,
//             int[] sensors, float geo_altitude, String squawk, boolean spi, int position_source){
//
//        this.Id = id;
//        this.icao = icao;
//        this.callsign = callsign;
//        this.origin_country = origin_country;
//        this.time_position = time_position;
//        this.last_contact = last_contact;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.baro_altitude = baro_altitude;
//        this.on_ground = on_ground;
//        this.velocity = velocity;
//        this.true_track = true_track;
//        this.vertical_rate = vertical_rate;
//        this.sensors = sensors;
//        this.geo_altitude = geo_altitude;
//        this.squawk = squawk;
//        this.spi = spi;
//        this.position_source = position_source;
//    }
//
//    public Long getId() {
//        return Id;
//    }
//
//    public void setId(Long id) {
//        Id = id;
//    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    public int getTime_position() {
        return time_position;
    }

    public void setTime_position(int time_position) {
        this.time_position = time_position;
    }

    public int getLast_contact() {
        return last_contact;
    }

    public void setLast_contact(int last_contact) {
        this.last_contact = last_contact;
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

    public float getBaro_altitude() {
        return baro_altitude;
    }

    public void setBaro_altitude(float baro_altitude) {
        this.baro_altitude = baro_altitude;
    }

    public boolean isOn_ground() {
        return on_ground;
    }

    public void setOn_ground(boolean on_ground) {
        this.on_ground = on_ground;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getTrue_track() {
        return true_track;
    }

    public void setTrue_track(float true_track) {
        this.true_track = true_track;
    }

    public float getVertical_rate() {
        return vertical_rate;
    }

    public void setVertical_rate(float vertical_rate) {
        this.vertical_rate = vertical_rate;
    }

    public int[] getSensors() {
        return sensors;
    }

    public void setSensors(int[] sensors) {
        this.sensors = sensors;
    }

    public float getGeo_altitude() {
        return geo_altitude;
    }

    public void setGeo_altitude(float geo_altitude) {
        this.geo_altitude = geo_altitude;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public boolean isSpi() {
        return spi;
    }

    public void setSpi(boolean spi) {
        this.spi = spi;
    }

    public int getPosition_source() {
        return position_source;
    }

    public void setPosition_source(int position_source) {
        this.position_source = position_source;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "icao24='" + icao + '\'' +
                ", callsign='" + callsign + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", time_position=" + time_position +
                ", last_contact=" + last_contact +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", baro_altitude=" + baro_altitude +
                ", on_ground=" + on_ground +
                ", velocity=" + velocity +
                ", true_track=" + true_track +
                ", vertical_rate=" + vertical_rate +
                ", sensors=" + Arrays.toString(sensors) +
                ", geo_altitude=" + geo_altitude +
                ", squawk='" + squawk + '\'' +
                ", spi=" + spi +
                ", position_source=" + position_source +
                '}' + "\n";
    }

}
