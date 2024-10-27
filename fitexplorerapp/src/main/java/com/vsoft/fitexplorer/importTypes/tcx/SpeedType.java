package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for SpeedType_t.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SpeedType_t"&gt;
 *   &lt;restriction base="{http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2}Token_t"&gt;
 *     &lt;enumeration value="Pace"/&gt;
 *     &lt;enumeration value="Speed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */

@XmlEnum
public enum SpeedType {

    @XmlEnumValue("Pace")
    PACE("Pace"),
    @XmlEnumValue("Speed")
    SPEED("Speed");
    private final String value;

    SpeedType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpeedType fromValue(String v) {
        for (SpeedType c: SpeedType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
