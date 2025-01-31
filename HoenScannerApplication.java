package com.hoen;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    private final List<SearchResult> searchResults = new ArrayList<>();

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
        // Initialization logic (if needed)
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Load rental car data
            List<SearchResult> rentalCars = objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("rental_cars.json"),
                new TypeReference<List<SearchResult>>() {}
            );

            // Load hotel data
            List<SearchResult> hotels = objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("hotels.json"),
                new TypeReference<List<SearchResult>>() {}
            );

            // Combine both datasets
            searchResults.addAll(rentalCars);
            searchResults.addAll(hotels);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JSON files", e);
        }

        System.out.println("Welcome to Hoen Scanner! Loaded " + searchResults.size() + " search results.");

        // Register the search resource endpoint
        environment.jersey().register(new SearchResource(searchResults));
    }
}
