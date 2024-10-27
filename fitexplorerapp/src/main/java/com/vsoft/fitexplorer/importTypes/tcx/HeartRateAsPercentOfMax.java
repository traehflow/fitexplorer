package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "HeartRateAsPercentOfMax_t", propOrder = {
    "value"
})
public class HeartRateAsPercentOfMax
    extends HeartRateValue
{

    @JacksonXmlProperty(localName = "Value")
    @XmlSchemaType(name = "unsignedByte")
    protected short value;

    /**
     * Gets the value of the value property.
     * 
     */
    public short getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(short value) {
        this.value = value;
    }

}
