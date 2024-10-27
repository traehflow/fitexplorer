package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;


public class Version {

    @JacksonXmlProperty(localName = "VersionMajor")
    @XmlSchemaType(name = "unsignedShort")
    protected int versionMajor;
    @JacksonXmlProperty(localName = "VersionMinor")
    @XmlSchemaType(name = "unsignedShort")
    protected int versionMinor;
    @JacksonXmlProperty(localName = "BuildMajor")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer buildMajor;
    @JacksonXmlProperty(localName = "BuildMinor")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer buildMinor;

    /**
     * Gets the value of the versionMajor property.
     * 
     */
    public int getVersionMajor() {
        return versionMajor;
    }

    /**
     * Sets the value of the versionMajor property.
     * 
     */
    public void setVersionMajor(int value) {
        this.versionMajor = value;
    }

    /**
     * Gets the value of the versionMinor property.
     * 
     */
    public int getVersionMinor() {
        return versionMinor;
    }

    /**
     * Sets the value of the versionMinor property.
     * 
     */
    public void setVersionMinor(int value) {
        this.versionMinor = value;
    }

    /**
     * Gets the value of the buildMajor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBuildMajor() {
        return buildMajor;
    }

    /**
     * Sets the value of the buildMajor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBuildMajor(Integer value) {
        this.buildMajor = value;
    }

    /**
     * Gets the value of the buildMinor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBuildMinor() {
        return buildMinor;
    }

    /**
     * Sets the value of the buildMinor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBuildMinor(Integer value) {
        this.buildMinor = value;
    }

}
