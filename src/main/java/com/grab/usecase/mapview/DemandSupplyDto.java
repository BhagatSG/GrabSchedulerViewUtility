package com.grab.usecase.mapview;

/**
 * Created by bhagat on 4/2/19.
 */
public class DemandSupplyDto {

    String geoHash;

    String date;

    String hourInterval;

    String minuteInterval;

    String demandSupplyRatio;

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

    public String getDemandSupplyRatio() {
        return demandSupplyRatio;
    }

    public DemandSupplyDto(String geoHash, String date, String hourInterval, String minuteInterval, String demandSupplyRatio) {
        this.geoHash = geoHash;
        this.date = date;
        this.hourInterval = hourInterval;
        this.minuteInterval = minuteInterval;
        this.demandSupplyRatio = demandSupplyRatio;
    }


}
