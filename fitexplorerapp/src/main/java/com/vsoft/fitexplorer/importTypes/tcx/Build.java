package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;



@Data
public class Build {

    @JacksonXmlProperty(localName = "Version")
    protected Version version;
    @JacksonXmlProperty(localName = "Type")
    @XmlSchemaType(name = "token")
    protected BuildType type;
    @JacksonXmlProperty(localName = "Time")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String time;
    @JacksonXmlProperty(localName = "Builder")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String builder;

}
