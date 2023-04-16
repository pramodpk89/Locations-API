// Import necessary classes and libraries
package com.ppk.locations.repository;

import com.ppk.locations.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

// Define a repository interface for managing Location entities
public interface LocationRepository extends JpaRepository<Location, Long> {
}
