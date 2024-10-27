package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlSeeAlso;


@XmlSeeAlso({
    Time.class,
    Distance.class,
    HeartRateAbove.class,
    HeartRateBelow.class,
    CaloriesBurned.class,
    UserInitiated.class
})
public abstract class Duration {


}
