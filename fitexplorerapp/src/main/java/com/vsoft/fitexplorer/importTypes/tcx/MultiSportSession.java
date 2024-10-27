package com.vsoft.fitexplorer.importTypes.tcx;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;




public class MultiSportSession {

    @JacksonXmlProperty(localName = "Id")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar id;
    @JacksonXmlProperty(localName = "FirstSport")
    protected FirstSport firstSport;
    @JacksonXmlProperty(localName = "NextSport")
    protected List<NextSport> nextSport;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setId(XMLGregorianCalendar value) {
        this.id = value;
    }

    /**
     * Gets the value of the firstSport property.
     * 
     * @return
     *     possible object is
     *     {@link FirstSport }
     *     
     */
    public FirstSport getFirstSport() {
        return firstSport;
    }

    /**
     * Sets the value of the firstSport property.
     * 
     * @param value
     *     allowed object is
     *     {@link FirstSport }
     *     
     */
    public void setFirstSport(FirstSport value) {
        this.firstSport = value;
    }

    /**
     * Gets the value of the nextSport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nextSport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNextSport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NextSport }
     * 
     * 
     */
    public List<NextSport> getNextSport() {
        if (nextSport == null) {
            nextSport = new ArrayList<NextSport>();
        }
        return this.nextSport;
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

}
