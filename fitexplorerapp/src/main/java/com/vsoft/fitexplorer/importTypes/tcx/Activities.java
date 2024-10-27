package com.vsoft.fitexplorer.importTypes.tcx;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "ActivityList_t", propOrder = {
    "Activity",
    "multiSportSession"
},
        namespace = "https://www8.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd")
public class Activities {

    @JacksonXmlProperty(localName = "Activity", namespace = "https://www8.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd")
    protected List<Activity> activity;
    @JacksonXmlProperty(localName = "MultiSportS ession")
    protected List<MultiSportSession> multiSportSession;

    /**
     * Gets the value of the activity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Activity }
     * 
     * 
     */
    public List<Activity> getActivity() {
        if (activity == null) {
            activity = new ArrayList<Activity>();
        }
        return this.activity;
    }

    /**
     * Gets the value of the multiSportSession property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiSportSession property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiSportSession().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiSportSession }
     * 
     * 
     */
    public List<MultiSportSession> getMultiSportSession() {
        if (multiSportSession == null) {
            multiSportSession = new ArrayList<MultiSportSession>();
        }
        return this.multiSportSession;
    }

}
