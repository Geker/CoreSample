package org.corejava.enumtest;

public enum EnumSample {
    CRON,
    CRON_SIMPLE,
    SERVICE;

    public boolean isCron() {
        if (this.equals(CRON) || this.equals(CRON_SIMPLE))
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        try {
            EnumSample ss = EnumSample.valueOf("ss");
        }
        catch (IllegalArgumentException e) {

        }
    }
}
