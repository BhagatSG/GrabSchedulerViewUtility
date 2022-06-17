package com.grab.usecase.mapview;

/**
 * Created by bhagat on 3/23/19.
 */
public class TrafficCongestionDto {
    String geoHash;

    String date;

    String hourInterval;

    String minuteInterval;

    String congestionStatus;

    public TrafficCongestionDto(String geoHash, String date, String hourInterval, String minuteInterval, String congestionStatus) {
        this.geoHash = geoHash;
        this.date = date;
        this.hourInterval = hourInterval;
        this.minuteInterval = minuteInterval;
        this.congestionStatus = congestionStatus;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public String getDate() {
        return date;
    }

    public String getHourInterval() {
        return hourInterval;
    }

    public String getMinuteInterval() {
        return minuteInterval;
    }

    public String getCongestionStatus() {
        return congestionStatus;
    }
}
