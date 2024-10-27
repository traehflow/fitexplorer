package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
public class Activity {

    @JacksonXmlProperty(localName = "Id")
    private String id;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Lap")
    protected List<Lap> lap;
    @JacksonXmlProperty(localName = "Notes")
    protected String notes;
    @JacksonXmlProperty(localName = "Training")
    protected trainingt training;
    @JacksonXmlProperty(localName = "Creator")
    protected Device creator;
    @JacksonXmlProperty(localName = "Extensions")
    protected Extensions extensions;

    @JacksonXmlProperty(localName = "Sport", isAttribute = true) // Sport is an attribute

    @XmlAttribute(name = "Sport")
    protected String sport;


}
