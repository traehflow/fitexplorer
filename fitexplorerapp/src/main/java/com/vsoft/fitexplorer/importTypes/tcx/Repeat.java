package com.vsoft.fitexplorer.importTypes.tcx;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlSchemaType;


public class Repeat
    extends AbstractStep
{

    @JacksonXmlProperty(localName = "Repetitions")
    @XmlSchemaType(name = "positiveInteger")
    protected int repetitions;
    @JacksonXmlProperty(localName = "Child")
    protected List<AbstractStep> child;

    /**
     * Gets the value of the repetitions property.
     * 
     */
    public int getRepetitions() {
        return repetitions;
    }

    /**
     * Sets the value of the repetitions property.
     * 
     */
    public void setRepetitions(int value) {
        this.repetitions = value;
    }

    /**
     * Gets the value of the child property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the child property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChild().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AbstractStep }
     * 
     * 
     */
    public List<AbstractStep> getChild() {
        if (child == null) {
            child = new ArrayList<AbstractStep>();
        }
        return this.child;
    }

}
