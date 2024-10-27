package com.vsoft.fitexplorer.importTypes.tcx;

import javax.xml.bind.annotation.XmlSeeAlso;


@XmlSeeAlso({
    PredefinedSpeedZone.class,
    CustomSpeedZone.class,
    PredefinedHeartRateZone.class,
    CustomHeartRateZone.class
})
public abstract class Zone {


}
