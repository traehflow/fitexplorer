package com.vsoft.fitexplorer.jpl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Generated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userData",
        indexes = {@Index(name = "idx_username", columnList = "username")})
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdata_id_seq")
    @SequenceGenerator(name = "userdata_id_seq", sequenceName = "userdata_id_seq", allocationSize = 500, initialValue = 100000000)
    int id;
    private String username;
    private String password;
    private Set<String> role;
}
