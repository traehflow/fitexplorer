package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class CustomHeartRateZone
    extends Zone
{

    @JacksonXmlProperty(localName = "Low")
    protected HeartRateValue low;
    @JacksonXmlProperty(localName = "High")
    protected HeartRateValue high;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateValue }
     *     
     */
    public HeartRateValue getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateValue }
     *     
     */
    public void setLow(HeartRateValue value) {
        this.low = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateValue }
     *     
     */
    public HeartRateValue getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateValue }
     *     
     */
    public void setHigh(HeartRateValue value) {
        this.high = value;
    }

}
