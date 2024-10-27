package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class trainingt {

    @JacksonXmlProperty(localName = "QuickWorkoutResults")
    protected QuickWorkout quickWorkoutResults;
    @JacksonXmlProperty(localName = "Plan")
    protected PlanT plan;
    @JacksonXmlProperty(localName = "VirtualPartner")
    protected boolean virtualPartner;

    /**
     * Gets the value of the quickWorkoutResults property.
     * 
     * @return
     *     possible object is
     *     {@link QuickWorkout }
     *     
     */
    public QuickWorkout getQuickWorkoutResults() {
        return quickWorkoutResults;
    }

    /**
     * Sets the value of the quickWorkoutResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuickWorkout }
     *     
     */
    public void setQuickWorkoutResults(QuickWorkout value) {
        this.quickWorkoutResults = value;
    }

    /**
     * Gets the value of the plan property.
     * 
     * @return
     *     possible object is
     *     {@link PlanT }
     *     
     */
    public PlanT getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanT }
     *     
     */
    public void setPlan(PlanT value) {
        this.plan = value;
    }

    /**
     * Gets the value of the virtualPartner property.
     * 
     */
    public boolean isVirtualPartner() {
        return virtualPartner;
    }

    /**
     * Sets the value of the virtualPartner property.
     * 
     */
    public void setVirtualPartner(boolean value) {
        this.virtualPartner = value;
    }

}
