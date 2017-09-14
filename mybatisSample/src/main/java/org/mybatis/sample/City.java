package org.mybatis.sample;

import java.io.Serializable;

public class City implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;
    private int cityId;
    private String city;
    private int countryId;
    private int lastUpdate

    ;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "City [cityId=" + cityId + ", city=" + city + ", countryId=" + countryId + ", lastUpdate=" + lastUpdate + "]";
    }

}
