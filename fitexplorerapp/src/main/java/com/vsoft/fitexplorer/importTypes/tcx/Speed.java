package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "Speed_t", propOrder = {
    "speedZone"
})
public class Speed
    extends Target
{

    @JacksonXmlProperty(localName = "SpeedZone")
    protected Zone speedZone;

    /**
     * Gets the value of the speedZone property.
     * 
     * @return
     *     possible object is
     *     {@link Zone }
     *     
     */
    public Zone getSpeedZone() {
        return speedZone;
    }

    /**
     * Sets the value of the speedZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone }
     *     
     */
    public void setSpeedZone(Zone value) {
        this.speedZone = value;
    }

}
