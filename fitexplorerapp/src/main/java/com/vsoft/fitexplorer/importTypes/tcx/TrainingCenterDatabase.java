
package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlType;
import java.util.List;



@XmlRootElement( name = "TrainingCenterDatabase", namespace = "https://www8.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd")

@XmlType(name = "TrainingCenterDatabase", propOrder = {
    "folders",
    "activities",
    "workouts",
    "courses",
    "author",
    "extensions"
},
namespace = "https://www8.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd")
public class TrainingCenterDatabase {

    @JacksonXmlProperty(localName = "Folders")
    private Folders folders;

/*    @JacksonXmlElementWrapper(localName = "Activities")
    @JacksonXmlProperty(localName = "Activity")
    @JacksonXmlProperty(localName = "Activities", namespace = "https://www8.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd")
    private Activities activities;*/

    @JacksonXmlElementWrapper(localName = "Activities")
    @JacksonXmlProperty(localName = "Activity")
    private List<Activity> activities;

    // Getters and setters
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

        @JacksonXmlProperty(localName = "Workouts")
    protected WorkoutList workouts;
    @JacksonXmlProperty(localName = "Courses")
    protected CourseList courses;
    @JacksonXmlProperty(localName = "Author")
    protected Device author;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    /**
     * Gets the value of the folders property.
     * 
     * @return
     *     possible object is
     *     {@link Folders }
     *     
     */
    public Folders getFolders() {
        return folders;
    }

    /**
     * Sets the value of the folders property.
     * 
     * @param value
     *     allowed object is
     *     {@link Folders }
     *     
     */
    public void setFolders(Folders value) {
        this.folders = value;
    }

/*
    */
/**
     * Gets the value of the activities property.
     * 
     * @return
     *     possible object is
     *     {@link Activities }
     *     
     *//*

    public Activities getActivities() {
        return activities;
    }

    */
/**
     * Sets the value of the activities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Activities }
     *     
     *//*

    public void setActivities(Activities value) {
        this.activities = value;
    }
*/

    /**
     * Gets the value of the workouts property.
     * 
     * @return
     *     possible object is
     *     {@link WorkoutList }
     *     
     */
    public WorkoutList getWorkouts() {
        return workouts;
    }

    /**
     * Sets the value of the workouts property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkoutList }
     *     
     */
    public void setWorkouts(WorkoutList value) {
        this.workouts = value;
    }

    /**
     * Gets the value of the courses property.
     * 
     * @return
     *     possible object is
     *     {@link CourseList }
     *     
     */
    public CourseList getCourses() {
        return courses;
    }

    /**
     * Sets the value of the courses property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseList }
     *     
     */
    public void setCourses(CourseList value) {
        this.courses = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link AbstractSource }
     *     
     */
    public Device getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractSource }
     *     
     */
    public void setAuthor(Device value) {
        this.author = value;
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
