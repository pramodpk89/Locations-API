package com.ppk.locations.dataLoader;

// Import necessary classes and libraries
import com.ppk.locations.model.Location;
import com.ppk.locations.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// Declare a component class for loading initial location data
@Component
public class LocationDataLoader {
    // Define a logger for this class
    private static final Logger log = LoggerFactory.getLogger(LocationDataLoader.class);

    // Define a bean that returns a CommandLineRunner that loads initial data into the LocationRepository
    @Bean
    CommandLineRunner loadData(LocationRepository repository) {
        return args -> {
            // Log and save initial location data for Bangalore, India
            log.info("Loading Bangalore " + repository.save(new Location(101, "Bangalore", 12.97, 77.59, "India", 10000000)));
            // Log and save initial location data for Mysore, India
            log.info("Loading Mysore " + repository.save(new Location(102, "Mysore", 12.29, 76.63, "India", 30000000)));
            // Log and save initial location data for Charlotte, United States
            log.info("Loading Charlotte " + repository.save(new Location(103, "Charlotte", 35.227, 80.84, "United States", 9000000)));
        };
    }
}
