package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Intensity {

    ACTIVE("Active"),
    RESTING("Resting");
    private final String value;

    Intensity(String v) {
        value = v;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static Intensity fromValue(String v) {
        for (Intensity c: Intensity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
