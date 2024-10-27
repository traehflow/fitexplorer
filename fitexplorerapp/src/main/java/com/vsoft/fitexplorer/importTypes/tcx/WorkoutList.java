package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "WorkoutList_t", propOrder = {
    "workout"
})
public class WorkoutList {

    @JacksonXmlProperty(localName = "Workout")
    protected List<Workout> workout;

    /**
     * Gets the value of the workout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Workout }
     * 
     * 
     */
    public List<Workout> getWorkout() {
        if (workout == null) {
            workout = new ArrayList<Workout>();
        }
        return this.workout;
    }

}
