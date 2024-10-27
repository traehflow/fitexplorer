package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

@Data
public class Lap {

    @JacksonXmlProperty(localName = "TotalTimeSeconds")
    protected double totalTimeSeconds;

    @JacksonXmlProperty(localName = "DistanceMeters")
    protected double distanceMeters;
    @JacksonXmlProperty(localName = "MaximumSpeed")
    protected Double maximumSpeed;
    @JacksonXmlProperty(localName = "Calories")
    @XmlSchemaType(name = "unsignedShort")
    protected double calories;
    @JacksonXmlProperty(localName = "AverageHeartRateBpm")
    protected HeartRateInBeatsPerMinute averageHeartRateBpm;
    @JacksonXmlProperty(localName = "MaximumHeartRateBpm")
    protected HeartRateInBeatsPerMinute maximumHeartRateBpm;
    @JacksonXmlProperty(localName = "Intensity")
    @XmlSchemaType(name = "token")
    protected Intensity intensity;
    @JacksonXmlProperty(localName = "Cadence")
    @XmlSchemaType(name = "unsignedByte")
    protected Short cadence;
    @JacksonXmlProperty(localName = "TriggerMethod")
    @XmlSchemaType(name = "token")
    protected TriggerMethod triggerMethod;

    @JacksonXmlElementWrapper(localName = "Track")
    @JacksonXmlProperty(localName = "Track")
    protected List<Trackpoint> track;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    @JacksonXmlProperty(localName = "StartTime", isAttribute = true)
    @XmlAttribute(name = "StartTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;

}
