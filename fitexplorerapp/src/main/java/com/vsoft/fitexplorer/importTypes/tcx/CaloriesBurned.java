package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CaloriesBurned_t", propOrder = {
    "calories"
})
public class CaloriesBurned
    extends Duration
{

    @JacksonXmlProperty(localName = "Calories")
    @XmlSchemaType(name = "unsignedShort")
    protected int calories;

    /**
     * Gets the value of the calories property.
     * 
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Sets the value of the calories property.
     * 
     */
    public void setCalories(int value) {
        this.calories = value;
    }

}
