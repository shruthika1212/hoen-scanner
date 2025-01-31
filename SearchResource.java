
package com.hoen;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final List<SearchResult> searchResults;

    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @POST
    public Response search(Search search) {
        List<SearchResult> filteredResults = searchResults.stream()
            .filter(result -> result.getCity().equalsIgnoreCase(search.getCity()))
            .collect(Collectors.toList());

        return Response.ok(filteredResults).build();
    }
}
