package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "Time_t", propOrder = {
    "seconds"
})
public class Time
    extends Duration
{

    @JacksonXmlProperty(localName = "Seconds")
    @XmlSchemaType(name = "unsignedShort")
    protected int seconds;

    /**
     * Gets the value of the seconds property.
     * 
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Sets the value of the seconds property.
     * 
     */
    public void setSeconds(int value) {
        this.seconds = value;
    }

}
