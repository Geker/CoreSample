package org.mybatis.sample;

import java.io.Serializable;
import java.util.Date;

public class Country implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 1L;
    private int countryId;
    private Date lastUpdate;
    private String country;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Country [countryId=" + countryId + ", lastUpdate=" + lastUpdate + ", country=" + country + "]";
    }

}
