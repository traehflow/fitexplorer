package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Courses {

    @JacksonXmlProperty(localName = "CourseFolder")
    protected CourseFolder courseFolder;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the courseFolder property.
     * 
     * @return
     *     possible object is
     *     {@link CourseFolder }
     *     
     */
    public CourseFolder getCourseFolder() {
        return courseFolder;
    }

    /**
     * Sets the value of the courseFolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseFolder }
     *     
     */
    public void setCourseFolder(CourseFolder value) {
        this.courseFolder = value;
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
