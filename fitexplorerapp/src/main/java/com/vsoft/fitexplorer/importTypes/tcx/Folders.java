package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Folders {

    @JacksonXmlProperty(localName = "History")
    protected History history;
    @JacksonXmlProperty(localName = "Workouts")
    protected Workouts workouts;
    @JacksonXmlProperty(localName = "Courses")
    protected Courses courses;

    /**
     * Gets the value of the history property.
     * 
     * @return
     *     possible object is
     *     {@link History }
     *     
     */
    public History getHistory() {
        return history;
    }

    /**
     * Sets the value of the history property.
     * 
     * @param value
     *     allowed object is
     *     {@link History }
     *     
     */
    public void setHistory(History value) {
        this.history = value;
    }

    /**
     * Gets the value of the workouts property.
     * 
     * @return
     *     possible object is
     *     {@link Workouts }
     *     
     */
    public Workouts getWorkouts() {
        return workouts;
    }

    /**
     * Sets the value of the workouts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Workouts }
     *     
     */
    public void setWorkouts(Workouts value) {
        this.workouts = value;
    }

    /**
     * Gets the value of the courses property.
     * 
     * @return
     *     possible object is
     *     {@link Courses }
     *     
     */
    public Courses getCourses() {
        return courses;
    }

    /**
     * Sets the value of the courses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Courses }
     *     
     */
    public void setCourses(Courses value) {
        this.courses = value;
    }

}
