package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class History {

    @JacksonXmlProperty(localName = "Running")
    protected historyfoldert running;
    @JacksonXmlProperty(localName = "Biking")
    protected historyfoldert biking;
    @JacksonXmlProperty(localName = "Other")
    protected historyfoldert other;
    @JacksonXmlProperty(localName = "MultiSport")
    protected MultiSportFolder multiSport;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the running property.
     * 
     * @return
     *     possible object is
     *     {@link historyfoldert }
     *     
     */
    public historyfoldert getRunning() {
        return running;
    }

    /**
     * Sets the value of the running property.
     * 
     * @param value
     *     allowed object is
     *     {@link historyfoldert }
     *     
     */
    public void setRunning(historyfoldert value) {
        this.running = value;
    }

    /**
     * Gets the value of the biking property.
     * 
     * @return
     *     possible object is
     *     {@link historyfoldert }
     *     
     */
    public historyfoldert getBiking() {
        return biking;
    }

    /**
     * Sets the value of the biking property.
     * 
     * @param value
     *     allowed object is
     *     {@link historyfoldert }
     *     
     */
    public void setBiking(historyfoldert value) {
        this.biking = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link historyfoldert }
     *     
     */
    public historyfoldert getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link historyfoldert }
     *     
     */
    public void setOther(historyfoldert value) {
        this.other = value;
    }

    /**
     * Gets the value of the multiSport property.
     * 
     * @return
     *     possible object is
     *     {@link MultiSportFolder }
     *     
     */
    public MultiSportFolder getMultiSport() {
        return multiSport;
    }

    /**
     * Sets the value of the multiSport property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiSportFolder }
     *     
     */
    public void setMultiSport(MultiSportFolder value) {
        this.multiSport = value;
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
