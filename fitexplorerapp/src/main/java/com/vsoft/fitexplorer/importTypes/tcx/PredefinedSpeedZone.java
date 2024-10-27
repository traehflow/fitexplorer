package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "PredefinedSpeedZone_t", propOrder = {
    "number"
})
public class PredefinedSpeedZone
    extends Zone
{

    @JacksonXmlProperty(localName = "Number")
    @XmlSchemaType(name = "positiveInteger")
    protected int number;

    /**
     * Gets the value of the number property.
     * 
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     */
    public void setNumber(int value) {
        this.number = value;
    }

}
