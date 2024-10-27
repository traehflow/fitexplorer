package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Workouts {

    @JacksonXmlProperty(localName = "Running")
    protected WorkoutFolder running;
    @JacksonXmlProperty(localName = "Biking")
    protected WorkoutFolder biking;
    @JacksonXmlProperty(localName = "Other")
    protected WorkoutFolder other;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the running property.
     * 
     * @return
     *     possible object is
     *     {@link WorkoutFolder }
     *     
     */
    public WorkoutFolder getRunning() {
        return running;
    }

    /**
     * Sets the value of the running property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkoutFolder }
     *     
     */
    public void setRunning(WorkoutFolder value) {
        this.running = value;
    }

    /**
     * Gets the value of the biking property.
     * 
     * @return
     *     possible object is
     *     {@link WorkoutFolder }
     *     
     */
    public WorkoutFolder getBiking() {
        return biking;
    }

    /**
     * Sets the value of the biking property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkoutFolder }
     *     
     */
    public void setBiking(WorkoutFolder value) {
        this.biking = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link WorkoutFolder }
     *     
     */
    public WorkoutFolder getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkoutFolder }
     *     
     */
    public void setOther(WorkoutFolder value) {
        this.other = value;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link Extensions }
     *     
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extensions }
     *     
     */
    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

}
