package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for Sport_t.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Sport_t"&gt;
 *   &lt;restriction base="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}Token_t"&gt;
 *     &lt;enumeration value="Running"/&gt;
 *     &lt;enumeration value="Biking"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */

@XmlEnum
public enum Sport {

    @XmlEnumValue("Running")
    RUNNING("Running"),
    @XmlEnumValue("Biking")
    BIKING("Biking"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    Sport(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Sport fromValue(String v) {
        for (Sport c: Sport.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
