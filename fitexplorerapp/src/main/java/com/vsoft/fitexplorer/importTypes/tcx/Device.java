package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;


@JsonTypeName("Device")
public class Device
    extends AbstractSource
{

    @JacksonXmlProperty(localName = "UnitId")
    @XmlSchemaType(name = "unsignedInt")
    protected long unitId;
    @JacksonXmlProperty(localName = "ProductID")
    @XmlSchemaType(name = "unsignedShort")
    protected int productID;
    @JacksonXmlProperty(localName = "Version")
    protected Version version;

    public Device() {

    }
    /**
     * Gets the value of the unitId property.
     * 
     */
    public long getUnitId() {
        return unitId;
    }

    /**
     * Sets the value of the unitId property.
     * 
     */
    public void setUnitId(long value) {
        this.unitId = value;
    }

    /**
     * Gets the value of the productID property.
     * 
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Sets the value of the productID property.
     * 
     */
    public void setProductID(int value) {
        this.productID = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Version }
     *     
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Version }
     *     
     */
    public void setVersion(Version value) {
        this.version = value;
    }

}
