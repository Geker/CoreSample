package org.corejava.bean.utils;

import java.util.Arrays;
import java.util.Map;

public class NewComputer {

    public NewComputer() {
        printClassName();
    }

	private String type;
	private String band;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	// nest Property
    private CorePart corePart;
    private String keyboard;
    private String mouse;

	private String buyTime;

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

    // Index Properties
    private String[] programs;

    // mappered Properties
    private Map<String, Object> othersParts;

    public String[] getPrograms() {
        return programs;
    }

    public void setPrograms(String[] programs) {
        this.programs = programs;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public CorePart getCorePart() {
        return corePart;
    }

    public void setCorePart(CorePart corePart) {
        this.corePart = corePart;
    }

    public Map<String, Object> getOthersParts() {
        return othersParts;
    }

    public void setOthersParts(Map<String, Object> othersParts) {
        this.othersParts = othersParts;
    }

    private static void printClassName() {
		System.out.println("NewComputer");
    }

	@Override
	public String toString() {
		return "NewComputer [type=" + type + ", band=" + band + ", corePart=" + corePart + ", keyboard=" + keyboard
				+ ", mouse=" + mouse + ", buyTime=" + buyTime + ", programs=" + Arrays.toString(programs)
				+ ", othersParts=" + othersParts + "]";
	}

}
