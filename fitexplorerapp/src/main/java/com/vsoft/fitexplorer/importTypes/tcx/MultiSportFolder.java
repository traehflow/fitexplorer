package com.vsoft.fitexplorer.importTypes.tcx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class MultiSportFolder {

    @JacksonXmlProperty(localName = "Folder")
    protected List<MultiSportFolder> folder;
    @JacksonXmlProperty(localName = "MultisportActivityRef")
    protected List<ActivityReference> multisportActivityRef;
    @JacksonXmlProperty(localName = "Week")
    protected List<Week> week;
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
     * {@link MultiSportFolder }
     * 
     * 
     */
    public List<MultiSportFolder> getFolder() {
        if (folder == null) {
            folder = new ArrayList<MultiSportFolder>();
        }
        return this.folder;
    }

    /**
     * Gets the value of the multisportActivityRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multisportActivityRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultisportActivityRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActivityReference }
     * 
     * 
     */
    public List<ActivityReference> getMultisportActivityRef() {
        if (multisportActivityRef == null) {
            multisportActivityRef = new ArrayList<ActivityReference>();
        }
        return this.multisportActivityRef;
    }

    /**
     * Gets the value of the week property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the week property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWeek().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Week }
     * 
     * 
     */
    public List<Week> getWeek() {
        if (week == null) {
            week = new ArrayList<Week>();
        }
        return this.week;
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
