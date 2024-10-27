package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for TrainingType_t.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TrainingType_t"&gt;
 *   &lt;restriction base="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}Token_t"&gt;
 *     &lt;enumeration value="Workout"/&gt;
 *     &lt;enumeration value="Course"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */

@XmlEnum
public enum TrainingType {

    @XmlEnumValue("Workout")
    WORKOUT("Workout"),
    @XmlEnumValue("Course")
    COURSE("Course");
    private final String value;

    TrainingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TrainingType fromValue(String v) {
        for (TrainingType c: TrainingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
