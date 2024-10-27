package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Cadence
    extends Target
{

    @JacksonXmlProperty(localName = "Low")
    protected double low;
    @JacksonXmlProperty(localName = "High")
    protected double high;

    /**
     * Gets the value of the low property.
     * 
     */
    public double getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     */
    public void setLow(double value) {
        this.low = value;
    }

    /**
     * Gets the value of the high property.
     * 
     */
    public double getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     */
    public void setHigh(double value) {
        this.high = value;
    }

}
