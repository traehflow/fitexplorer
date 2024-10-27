package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "HeartRateBelow_t", propOrder = {
    "heartRate"
})
public class HeartRateBelow
    extends Duration
{

    @JacksonXmlProperty(localName = "HeartRate")
    protected HeartRateValue heartRate;

    /**
     * Gets the value of the heartRate property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateValue }
     *     
     */
    public HeartRateValue getHeartRate() {
        return heartRate;
    }

    /**
     * Sets the value of the heartRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateValue }
     *     
     */
    public void setHeartRate(HeartRateValue value) {
        this.heartRate = value;
    }

}
