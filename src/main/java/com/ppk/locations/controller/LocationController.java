package com.ppk.locations.controller;

// Import necessary classes and libraries
import com.ppk.locations.exception.LocationBlankEception;
import com.ppk.locations.exception.LocationNotFoundException;
import com.ppk.locations.model.Location;
import com.ppk.locations.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Declare a RestController class for location management
@RestController
@AllArgsConstructor
public class LocationController {
    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    // Inject LocationRepository using constructor injection
    LocationRepository repository;

    // Create a new location and return it with associated HATEOAS links
    @PostMapping("/locations")
    @ResponseStatus(HttpStatus.CREATED)
    EntityModel<Location> createLocation(@RequestBody Location location) {
        if (location.getName().isBlank()) throw new LocationBlankEception();
        repository.save(location);
        logger.info("Location created: {}", location.getName());
        return EntityModel.of(location,
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("getAllLocations"),
                linkTo(methodOn(LocationController.class).getLocationById(location.getLocationId())).withRel("getLocationById"),
                linkTo(methodOn(LocationController.class).updateLocation(location, location.getLocationId())).withRel("updateLocation")
        );
    }

    // Retrieve all locations and return them as a list
    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    List<Location> getAllLocations() {
        List<Location> locations = repository.findAll();
        logger.info("All locations retrieved");
        return locations;
    }

    // Retrieve a specific location by its ID and return it with associated HATEOAS links
    @GetMapping("locations/{id}")
    EntityModel<Location> getLocationById(@PathVariable long id) {
        Location location = repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
        logger.info("Location with ID {} retrieved", id);
        return EntityModel.of(location,
                linkTo(methodOn(LocationController.class).getLocationById(id)).withSelfRel(),
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("getAllLocations"));
    }

    // Update an existing location with new data or create a new location if it doesn't exist
    @PostMapping("locations/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Location updateLocation(@RequestBody Location location, @PathVariable long id) {
        Location updatedLocation = repository.findById(id).map(loc -> {
            loc.setName(location.getName());
            loc.setLatitude(location.getLatitude());
            loc.setLongitude(location.getLongitude());
            loc.setCountry(location.getCountry());
            loc.setPopulation(location.getPopulation());
            return repository.save(location);
        }).orElseGet(() -> {
            return repository.save(location);
        });

        logger.info("Location with ID {} updated: {}", id, updatedLocation);
        return updatedLocation;
    }

    // Delete a location by its ID and return a confirmation message
    @DeleteMapping("locations/{id}")
    @ResponseStatus(HttpStatus.OK)
    String deleteLocation(@PathVariable long id) {
        Location location = repository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
        repository.deleteById(id);
        logger.info("Location with ID {} deleted", id);
        return "Delete successful!";
    }
}
