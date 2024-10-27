package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlSchemaType;


public class CourseLap {

    @JacksonXmlProperty(localName = "TotalTimeSeconds")
    protected double totalTimeSeconds;
    @JacksonXmlProperty(localName = "DistanceMeters")
    protected double distanceMeters;
    @JacksonXmlProperty(localName = "BeginPosition")
    protected Position beginPosition;
    @JacksonXmlProperty(localName = "BeginAltitudeMeters")
    protected Double beginAltitudeMeters;
    @JacksonXmlProperty(localName = "EndPosition")
    protected Position endPosition;
    @JacksonXmlProperty(localName = "EndAltitudeMeters")
    protected Double endAltitudeMeters;
    @JacksonXmlProperty(localName = "AverageHeartRateBpm")
    protected HeartRateInBeatsPerMinute averageHeartRateBpm;
    @JacksonXmlProperty(localName = "MaximumHeartRateBpm")
    protected HeartRateInBeatsPerMinute maximumHeartRateBpm;
    @JacksonXmlProperty(localName = "Intensity")
    @XmlSchemaType(name = "token")
    protected Intensity intensity;
    @JacksonXmlProperty(localName = "Cadence")
    @XmlSchemaType(name = "unsignedByte")
    protected Short cadence;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the totalTimeSeconds property.
     * 
     */
    public double getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    /**
     * Sets the value of the totalTimeSeconds property.
     * 
     */
    public void setTotalTimeSeconds(double value) {
        this.totalTimeSeconds = value;
    }

    /**
     * Gets the value of the distanceMeters property.
     * 
     */
    public double getDistanceMeters() {
        return distanceMeters;
    }

    /**
     * Sets the value of the distanceMeters property.
     * 
     */
    public void setDistanceMeters(double value) {
        this.distanceMeters = value;
    }

    /**
     * Gets the value of the beginPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getBeginPosition() {
        return beginPosition;
    }

    /**
     * Sets the value of the beginPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setBeginPosition(Position value) {
        this.beginPosition = value;
    }

    /**
     * Gets the value of the beginAltitudeMeters property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBeginAltitudeMeters() {
        return beginAltitudeMeters;
    }

    /**
     * Sets the value of the beginAltitudeMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBeginAltitudeMeters(Double value) {
        this.beginAltitudeMeters = value;
    }

    /**
     * Gets the value of the endPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getEndPosition() {
        return endPosition;
    }

    /**
     * Sets the value of the endPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setEndPosition(Position value) {
        this.endPosition = value;
    }

    /**
     * Gets the value of the endAltitudeMeters property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEndAltitudeMeters() {
        return endAltitudeMeters;
    }

    /**
     * Sets the value of the endAltitudeMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEndAltitudeMeters(Double value) {
        this.endAltitudeMeters = value;
    }

    /**
     * Gets the value of the averageHeartRateBpm property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public HeartRateInBeatsPerMinute getAverageHeartRateBpm() {
        return averageHeartRateBpm;
    }

    /**
     * Sets the value of the averageHeartRateBpm property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public void setAverageHeartRateBpm(HeartRateInBeatsPerMinute value) {
        this.averageHeartRateBpm = value;
    }

    /**
     * Gets the value of the maximumHeartRateBpm property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public HeartRateInBeatsPerMinute getMaximumHeartRateBpm() {
        return maximumHeartRateBpm;
    }

    /**
     * Sets the value of the maximumHeartRateBpm property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public void setMaximumHeartRateBpm(HeartRateInBeatsPerMinute value) {
        this.maximumHeartRateBpm = value;
    }

    /**
     * Gets the value of the intensity property.
     * 
     * @return
     *     possible object is
     *     {@link Intensity }
     *     
     */
    public Intensity getIntensity() {
        return intensity;
    }

    /**
     * Sets the value of the intensity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Intensity }
     *     
     */
    public void setIntensity(Intensity value) {
        this.intensity = value;
    }

    /**
     * Gets the value of the cadence property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCadence() {
        return cadence;
    }

    /**
     * Sets the value of the cadence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCadence(Short value) {
        this.cadence = value;
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
