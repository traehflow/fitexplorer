package com.vsoft.fitexplorer.jpl.entity;

import com.garmin.fit.DateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@Generated
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fitunit",
        indexes = {@Index(name = "idx_time", columnList = "fitActivity_id,timestamp")})
//@IdClass(FitUnitId.class)
public class FitUnit {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitactivity_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FitActivity fitActivity;
    @Id
    long timestamp;
    private Double latitude;
    private Double longitude;
    private Float altitude;
    private Short heartRate;
    private Float distance;
    private Byte temperature;

}


