// Import necessary classes and libraries
package com.ppk.locations.exception;

// Define a custom exception class for handling blank location names
public class LocationBlankEception extends RuntimeException {
    // Default constructor for the custom exception class, providing a predefined error message
    public LocationBlankEception() {
        super("Location name cannot be blank");
    }
}
