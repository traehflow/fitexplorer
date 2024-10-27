package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;



@Data
public class Workout {
    @JacksonXmlProperty(localName = "Name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @JacksonXmlProperty(localName = "Step")
    protected List<AbstractStep> step;
    @JacksonXmlProperty(localName = "ScheduledOn")
    @XmlSchemaType(name = "date")
    protected List<XMLGregorianCalendar> scheduledOn;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;
    @JacksonXmlProperty(localName = "Creator")
    protected Device creator;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;
    @XmlAttribute(name = "Sport")
    protected Sport sport;


    public List<AbstractStep> getStep() {
        if (step == null) {
            step = new ArrayList<AbstractStep>();
        }
        return this.step;
    }

    public List<XMLGregorianCalendar> getScheduledOn() {
        if (scheduledOn == null) {
            scheduledOn = new ArrayList<XMLGregorianCalendar>();
        }
        return this.scheduledOn;
    }


}
