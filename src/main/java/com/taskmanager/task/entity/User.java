package com.taskmanager.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;


    public void setTimezone(String timezone) {
        try {

            ZonedDateTime nowInZone = ZonedDateTime.now(ZoneId.of(timezone));
            ZonedDateTime nowInUTC = nowInZone.withZoneSameInstant(ZoneId.of("UTC"));

            this.timezone = nowInUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));
        } catch (Exception e) {
            this.timezone = "UTC";
        }
    }
}
