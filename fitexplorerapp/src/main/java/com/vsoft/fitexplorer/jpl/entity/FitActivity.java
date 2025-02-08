package com.vsoft.fitexplorer.jpl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Generated
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fitactivity",
        indexes = {@Index(name = "startTime", columnList = "startTime")})
public class FitActivity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitactivity_id_seq")
    @SequenceGenerator(name = "fitactivity_id_seq", sequenceName = "fitactivity_id_seq", allocationSize = 500, initialValue = 100000000)

    int id;
    long startTime;
    String originalFile;
    @ManyToOne
    UserData userData;
    @JoinColumn(name = "fitactivity_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.LAZY)
    List<FitUnit> fitUnitList;
    FitActivityType fitActivityType;
    //==================
    String activityId;
    String activityName;
    String description;
    Date startTimeLocal;
    Double distance;
    Double duration;
    Double elapsedDuration;
    Double movingDuration;
    Double elevationGain;
    Double elevationLoss;
    Double averageSpeed;
    Double maxSpeed;

}
