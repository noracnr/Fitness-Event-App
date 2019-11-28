package com.example.fitnessevent.event;

import com.example.fitnessevent.model.PaginatedEvents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventbriteApi {

    @GET("/v3/events/search/")
    Call<PaginatedEvents> getPaginatedEvents(@Query("categories") int categories, @Query("page") Integer[] page, @Query("expand") String expand, @Query("token") String token);

}
