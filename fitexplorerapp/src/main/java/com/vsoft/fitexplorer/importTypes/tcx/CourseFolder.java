package com.vsoft.fitexplorer.importTypes.tcx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class CourseFolder {

    @JacksonXmlProperty(localName = "Folder")
    protected List<CourseFolder> folder;
    @JacksonXmlProperty(localName = "CourseNameRef")
    protected List<NameKeyReference> courseNameRef;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;
    @XmlAttribute(name = "Name")
    protected String name;

    /**
     * Gets the value of the folder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the folder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFolder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseFolder }
     * 
     * 
     */
    public List<CourseFolder> getFolder() {
        if (folder == null) {
            folder = new ArrayList<CourseFolder>();
        }
        return this.folder;
    }

    /**
     * Gets the value of the courseNameRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the courseNameRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCourseNameRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameKeyReference }
     * 
     * 
     */
    public List<NameKeyReference> getCourseNameRef() {
        if (courseNameRef == null) {
            courseNameRef = new ArrayList<NameKeyReference>();
        }
        return this.courseNameRef;
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

}
