package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


public class Course {

    @JacksonXmlProperty(localName = "Name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @JacksonXmlProperty(localName = "Lap")
    protected List<CourseLap> lap;
    @JacksonXmlProperty(localName = "Track")
    protected List<Track> track;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;
    @JacksonXmlProperty(localName = "CoursePoint")
    protected List<CoursePoint> coursePoint;
    @JacksonXmlProperty(localName = "Creator")
    protected Device creator;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the lap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseLap }
     * 
     * 
     */
    public List<CourseLap> getLap() {
        if (lap == null) {
            lap = new ArrayList<CourseLap>();
        }
        return this.lap;
    }

    /**
     * Gets the value of the track property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the track property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrack().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Track }
     * 
     * 
     */
    public List<Track> getTrack() {
        if (track == null) {
            track = new ArrayList<Track>();
        }
        return this.track;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the coursePoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coursePoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoursePoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoursePoint }
     * 
     * 
     */
    public List<CoursePoint> getCoursePoint() {
        if (coursePoint == null) {
            coursePoint = new ArrayList<CoursePoint>();
        }
        return this.coursePoint;
    }

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractSource }
     *     
     */
    public Device getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractSource }
     *     
     */
    public void setCreator(Device value) {
        this.creator = value;
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
