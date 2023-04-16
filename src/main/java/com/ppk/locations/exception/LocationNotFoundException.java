// Import necessary classes and libraries
package com.ppk.locations.exception;

// Define a custom exception class for handling cases when a location is not found
public class LocationNotFoundException extends RuntimeException {

    // Constructor for the custom exception class, providing a predefined error message with the given location ID
    public LocationNotFoundException(long id) {
        super("Could not find location " + id);
    }
}
