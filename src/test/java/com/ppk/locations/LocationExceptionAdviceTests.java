package com.ppk.locations;

import com.ppk.locations.controller.LocationController;
import com.ppk.locations.exception.LocationBlankEception;
import com.ppk.locations.exception.LocationNotFoundException;
import com.ppk.locations.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
class LocationExceptionAdviceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepository repository;

    @Test
    void locationNotFoundExceptionTest() throws Exception {
        when(repository.findById(1L)).thenThrow(new LocationNotFoundException(1L));

        mockMvc.perform(get("/locations/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find location 1"));
    }

    @Test
    void locationBlankEceptionTest() throws Exception {
        String locationJson = "{\"locationId\": 1, \"name\": \"\", \"latitude\": 12.34, \"longitude\": 56.78, \"country\": \"Test Country\", \"population\": 100000}";

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Location name cannot be blank"));
    }
}
