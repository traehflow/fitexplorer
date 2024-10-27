package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "Track_t", propOrder = {
    "trackpoint"
})
public class Track {

    @JacksonXmlElementWrapper(localName = "Track")
    @JacksonXmlProperty(localName = "Trackpoint")
    protected List<Trackpoint> trackpoint;

    /**
     * Gets the value of the trackpoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackpoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackpoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Trackpoint }
     * 
     * 
     */
    public List<Trackpoint> getTrackpoint() {
        if (trackpoint == null) {
            trackpoint = new ArrayList<Trackpoint>();
        }
        return this.trackpoint;
    }

}
