package com.vsoft.fitexplorer.importTypes.tcx;

import com.fasterxml.jackson.annotation.JsonValue;


public enum TriggerMethod {

    MANUAL("Manual"),
    DISTANCE("Distance"),
    LOCATION("Location"),
    TIME("Time"),
    HEART_RATE("HeartRate");
    private final String value;

    TriggerMethod(String v) {
        value = v;
    }


    @JsonValue
    public String value() {
        return value;
    }

    public static TriggerMethod fromValue(String v) {
        for (TriggerMethod c: TriggerMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
