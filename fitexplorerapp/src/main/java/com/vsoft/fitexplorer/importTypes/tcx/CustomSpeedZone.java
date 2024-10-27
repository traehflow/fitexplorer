package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;


public class CustomSpeedZone
    extends Zone
{

    @JacksonXmlProperty(localName = "ViewAs")
    @XmlSchemaType(name = "token")
    protected SpeedType viewAs;
    @JacksonXmlProperty(localName = "LowInMetersPerSecond")
    protected double lowInMetersPerSecond;
    @JacksonXmlProperty(localName = "HighInMetersPerSecond")
    protected double highInMetersPerSecond;

    /**
     * Gets the value of the viewAs property.
     * 
     * @return
     *     possible object is
     *     {@link SpeedType }
     *     
     */
    public SpeedType getViewAs() {
        return viewAs;
    }

    /**
     * Sets the value of the viewAs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpeedType }
     *     
     */
    public void setViewAs(SpeedType value) {
        this.viewAs = value;
    }

    /**
     * Gets the value of the lowInMetersPerSecond property.
     * 
     */
    public double getLowInMetersPerSecond() {
        return lowInMetersPerSecond;
    }

    /**
     * Sets the value of the lowInMetersPerSecond property.
     * 
     */
    public void setLowInMetersPerSecond(double value) {
        this.lowInMetersPerSecond = value;
    }

    /**
     * Gets the value of the highInMetersPerSecond property.
     * 
     */
    public double getHighInMetersPerSecond() {
        return highInMetersPerSecond;
    }

    /**
     * Sets the value of the highInMetersPerSecond property.
     * 
     */
    public void setHighInMetersPerSecond(double value) {
        this.highInMetersPerSecond = value;
    }

}
