package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum BuildType {

    @XmlEnumValue("Internal")
    INTERNAL("Internal"),
    @XmlEnumValue("Alpha")
    ALPHA("Alpha"),
    @XmlEnumValue("Beta")
    BETA("Beta"),
    @XmlEnumValue("Release")
    RELEASE("Release");
    private final String value;

    BuildType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BuildType fromValue(String v) {
        for (BuildType c: BuildType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
