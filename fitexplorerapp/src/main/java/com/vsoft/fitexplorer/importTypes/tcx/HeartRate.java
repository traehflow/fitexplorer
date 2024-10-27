package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "HeartRate_t", propOrder = {
    "heartRateZone"
})
public class HeartRate
    extends Target
{

    @JacksonXmlProperty(localName = "HeartRateZone")
    protected Zone heartRateZone;

    /**
     * Gets the value of the heartRateZone property.
     * 
     * @return
     *     possible object is
     *     {@link Zone }
     *     
     */
    public Zone getHeartRateZone() {
        return heartRateZone;
    }

    /**
     * Sets the value of the heartRateZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone }
     *     
     */
    public void setHeartRateZone(Zone value) {
        this.heartRateZone = value;
    }

}
