// Import necessary classes and libraries
package com.ppk.locations.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

// Declare an entity class representing a location
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    // Declare the locationId field as the primary key of the entity
    @Id
    private long locationId;

    // Declare other fields of the entity
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private long population;

    // The Lombok annotations generate the following for the class:
    // - Getter and Setter methods for all fields
    // - All-args constructor
    // - No-args constructor
}
