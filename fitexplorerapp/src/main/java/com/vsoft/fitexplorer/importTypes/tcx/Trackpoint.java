package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;




public class Trackpoint {

    @JacksonXmlProperty(localName = "Time")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @JacksonXmlProperty(localName = "Position")
    protected Position position;
    @JacksonXmlProperty(localName = "AltitudeMeters")
    protected Double altitudeMeters;
    @JacksonXmlProperty(localName = "DistanceMeters")
    protected Double distanceMeters;
    @JacksonXmlProperty(localName = "HeartRateBpm")
    protected HeartRateInBeatsPerMinute heartRateBpm;
    @JacksonXmlProperty(localName = "Cadence")
    @XmlSchemaType(name = "unsignedByte")
    protected Short cadence;
    @JacksonXmlProperty(localName = "SensorState")
    @XmlSchemaType(name = "token")
    protected SensorState sensorState;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setPosition(Position value) {
        this.position = value;
    }

    /**
     * Gets the value of the altitudeMeters property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAltitudeMeters() {
        return altitudeMeters;
    }

    /**
     * Sets the value of the altitudeMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAltitudeMeters(Double value) {
        this.altitudeMeters = value;
    }

    /**
     * Gets the value of the distanceMeters property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceMeters() {
        return distanceMeters;
    }

    /**
     * Sets the value of the distanceMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceMeters(Double value) {
        this.distanceMeters = value;
    }

    /**
     * Gets the value of the heartRateBpm property.
     * 
     * @return
     *     possible object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public HeartRateInBeatsPerMinute getHeartRateBpm() {
        return heartRateBpm;
    }

    /**
     * Sets the value of the heartRateBpm property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeartRateInBeatsPerMinute }
     *     
     */
    public void setHeartRateBpm(HeartRateInBeatsPerMinute value) {
        this.heartRateBpm = value;
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
     * Gets the value of the sensorState property.
     * 
     * @return
     *     possible object is
     *     {@link SensorState }
     *     
     */
    public SensorState getSensorState() {
        return sensorState;
    }

    /**
     * Sets the value of the sensorState property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorState }
     *     
     */
    public void setSensorState(SensorState value) {
        this.sensorState = value;
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
