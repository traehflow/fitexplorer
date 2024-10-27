package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Position {

    @JacksonXmlProperty(localName = "LatitudeDegrees")
    protected double latitudeDegrees;
    @JacksonXmlProperty(localName = "LongitudeDegrees")
    protected double longitudeDegrees;

    /**
     * Gets the value of the latitudeDegrees property.
     * 
     */
    public double getLatitudeDegrees() {
        return latitudeDegrees;
    }

    /**
     * Sets the value of the latitudeDegrees property.
     * 
     */
    public void setLatitudeDegrees(double value) {
        this.latitudeDegrees = value;
    }

    /**
     * Gets the value of the longitudeDegrees property.
     * 
     */
    public double getLongitudeDegrees() {
        return longitudeDegrees;
    }

    /**
     * Sets the value of the longitudeDegrees property.
     * 
     */
    public void setLongitudeDegrees(double value) {
        this.longitudeDegrees = value;
    }

}
