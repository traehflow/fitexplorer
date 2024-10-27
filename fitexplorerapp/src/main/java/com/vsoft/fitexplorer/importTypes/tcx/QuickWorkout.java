package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class QuickWorkout {

    @JacksonXmlProperty(localName = "TotalTimeSeconds")
    protected double totalTimeSeconds;
    @JacksonXmlProperty(localName = "DistanceMeters")
    protected double distanceMeters;

    /**
     * Gets the value of the totalTimeSeconds property.
     * 
     */
    public double getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    /**
     * Sets the value of the totalTimeSeconds property.
     * 
     */
    public void setTotalTimeSeconds(double value) {
        this.totalTimeSeconds = value;
    }

    /**
     * Gets the value of the distanceMeters property.
     * 
     */
    public double getDistanceMeters() {
        return distanceMeters;
    }

    /**
     * Sets the value of the distanceMeters property.
     * 
     */
    public void setDistanceMeters(double value) {
        this.distanceMeters = value;
    }

}
