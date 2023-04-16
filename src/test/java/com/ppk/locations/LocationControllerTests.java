package com.ppk.locations;

import com.ppk.locations.model.Location;
import com.ppk.locations.repository.LocationRepository;
import com.ppk.locations.controller.LocationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepository repository;

    @Test
    void createLocation() throws Exception {
        Location location = new Location(1, "Test City", 12.34, 56.78, "Test Country", 100000);
        when(repository.save(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationId\":1,\"name\":\"Test City\",\"latitude\":12.34,\"longitude\":56.78,\"country\":\"Test Country\",\"population\":100000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test City"));
    }

    @Test
    void getAllLocations() throws Exception {
        Location location1 = new Location(1, "Test City 1", 12.34, 56.78, "Test Country", 100000);
        Location location2 = new Location(2, "Test City 2", 23.45, 67.89, "Test Country", 200000);

        given(repository.findAll()).willReturn(Arrays.asList(location1, location2));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test City 1"))
                .andExpect(jsonPath("$[1].name").value("Test City 2"));
    }

    @Test
    void updateLocation() throws Exception {
        Location location = new Location(1, "Test City", 12.34, 56.78, "Test Country", 100000);
        when(repository.findById(1L)).thenReturn(Optional.of(location));
        when(repository.save(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/locations/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationId\":1,\"name\":\"Updated Test City\",\"latitude\":12.34,\"longitude\":56.78,\"country\":\"Test Country\",\"population\":100000}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Updated Test City"));
    }

    @Test
    void deleteLocation() throws Exception {
        Location location = new Location(1, "Test City", 12.34, 56.78, "Test Country", 100000);
        given(repository.findById(1L)).willReturn(Optional.of(location));
        doNothing().when(repository).deleteById(1L);

        mockMvc.perform(delete("/locations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Delete successful!"));
    }

}
