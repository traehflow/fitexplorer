package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;




@Getter
@ToString
public class Trackpoint {

    /**
     * -- GETTER --
     *  Gets the value of the time property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     */
    @JacksonXmlProperty(localName = "Time")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    /**
     * -- GETTER --
     *  Gets the value of the position property.
     *
     * @return
     *     possible object is
     *     {@link Position }
     */
    @JacksonXmlProperty(localName = "Position")
    protected Position position;
    /**
     * -- GETTER --
     *  Gets the value of the altitudeMeters property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     */
    @JacksonXmlProperty(localName = "AltitudeMeters")
    protected Double altitudeMeters;
    /**
     * -- GETTER --
     *  Gets the value of the distanceMeters property.
     *
     * @return
     *     possible object is
     *     {@link Double }
     */
    @JacksonXmlProperty(localName = "DistanceMeters")
    protected Double distanceMeters;
    /**
     * -- GETTER --
     *  Gets the value of the heartRateBpm property.
     *
     * @return
     *     possible object is
     *     {@link HeartRateInBeatsPerMinute }
     */
    @JacksonXmlProperty(localName = "HeartRateBpm")
    protected HeartRateInBeatsPerMinute heartRateBpm;
    /**
     * -- GETTER --
     *  Gets the value of the cadence property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     */
    @JacksonXmlProperty(localName = "Cadence")
    @XmlSchemaType(name = "unsignedByte")
    protected Short cadence;
    /**
     * -- GETTER --
     *  Gets the value of the sensorState property.
     *
     * @return
     *     possible object is
     *     {@link SensorState }
     */
    @JacksonXmlProperty(localName = "SensorState")
    @XmlSchemaType(name = "token")
    protected SensorState sensorState;
    /**
     * -- GETTER --
     *  Gets the value of the extensions property.
     *
     * @return
     *     possible object is
     *     {@link Extensions }
     */
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

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
