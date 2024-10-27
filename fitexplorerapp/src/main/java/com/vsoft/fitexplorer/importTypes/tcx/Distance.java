package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "Distance_t", propOrder = {
    "meters"
})
public class Distance
    extends Duration
{

    @JacksonXmlProperty(localName = "Meters")
    @XmlSchemaType(name = "unsignedShort")
    protected int meters;

    /**
     * Gets the value of the meters property.
     * 
     */
    public int getMeters() {
        return meters;
    }

    /**
     * Sets the value of the meters property.
     * 
     */
    public void setMeters(int value) {
        this.meters = value;
    }

}
