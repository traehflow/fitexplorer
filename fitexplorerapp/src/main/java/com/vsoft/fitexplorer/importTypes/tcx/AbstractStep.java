package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AbstractStep_t", propOrder = {
    "stepId"
})
@XmlSeeAlso({
    Repeat.class,
    Step.class
})
public abstract class AbstractStep {

    @JacksonXmlProperty(localName = "StepId")
    @XmlSchemaType(name = "positiveInteger")
    protected int stepId;

    /**
     * Gets the value of the stepId property.
     * 
     */
    public int getStepId() {
        return stepId;
    }

    /**
     * Sets the value of the stepId property.
     * 
     */
    public void setStepId(int value) {
        this.stepId = value;
    }

}
