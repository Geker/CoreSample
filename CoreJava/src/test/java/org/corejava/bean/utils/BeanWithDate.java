package org.corejava.bean.utils;

import java.util.Date;

public class BeanWithDate {
    private Date curDate;

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BeanWithDate [curDate=" + curDate + ", value=" + value + "]";
    }

}
