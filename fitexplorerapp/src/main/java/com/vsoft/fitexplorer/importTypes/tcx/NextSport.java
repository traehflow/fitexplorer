package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class NextSport {

    @JacksonXmlProperty(localName = "Transition")
    protected Lap transition;
    @JacksonXmlProperty(localName = "Activity")
    protected Activity activity;

    /**
     * Gets the value of the transition property.
     * 
     * @return
     *     possible object is
     *     {@link Lap }
     *     
     */
    public Lap getTransition() {
        return transition;
    }

    /**
     * Sets the value of the transition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lap }
     *     
     */
    public void setTransition(Lap value) {
        this.transition = value;
    }

    /**
     * Gets the value of the activity property.
     * 
     * @return
     *     possible object is
     *     {@link Activity }
     *     
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Sets the value of the activity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Activity }
     *     
     */
    public void setActivity(Activity value) {
        this.activity = value;
    }

}
