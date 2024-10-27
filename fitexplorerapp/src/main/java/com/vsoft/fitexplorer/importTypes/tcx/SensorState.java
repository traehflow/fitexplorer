package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for SensorState_t.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SensorState_t"&gt;
 *   &lt;restriction base="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}Token_t"&gt;
 *     &lt;enumeration value="Present"/&gt;
 *     &lt;enumeration value="Absent"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */

@XmlEnum
public enum SensorState {

    @XmlEnumValue("Present")
    PRESENT("Present"),
    @XmlEnumValue("Absent")
    ABSENT("Absent");
    private final String value;

    SensorState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SensorState fromValue(String v) {
        for (SensorState c: SensorState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
