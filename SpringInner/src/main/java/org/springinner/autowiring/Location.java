package org.springinner.autowiring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springinner.annotation.DynaAnnotation;

@Component
public class Location {

	private static String ss = "sts";
	@DynaAnnotation(val = "#j")
	private String v;
    int i = 100;
	int j = 99;
	@Value("#{${len}.concat(' world')}")
	String vv;
    public int getI() {

        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
	public String toString() {
		return "Location [v=" + v + ", i=" + i + ", j=" + j + ", vv=" + vv + "]";
	}

}
